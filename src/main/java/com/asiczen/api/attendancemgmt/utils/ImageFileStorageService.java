package com.asiczen.api.attendancemgmt.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.asiczen.api.attendancemgmt.exception.FileStorageException;

@Service
public class ImageFileStorageService {

	private static final Logger logger = LoggerFactory.getLogger(ImageFileStorageService.class);
	
	public String storeFile(MultipartFile file, String targtFilename, Path fileStorageLocation) {

		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		File destinationDirectory = new File(fileStorageLocation.toString());

		try {

			if (!Files.isDirectory(fileStorageLocation)) {
				throw new FileStorageException("Image Directory missing");
			}

			Path targetLocation = destinationDirectory.toPath().resolve(targtFilename+".jpeg");
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			
			logger.debug("File stored successfully on server");
			
			return targtFilename;

		} catch (IOException io) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", io);
		} catch (Exception ep) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ep);
		}
	}
}
