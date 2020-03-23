package com.asiczen.api.attendancemgmt.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.asiczen.api.attendancemgmt.exception.FileStorageException;
import com.asiczen.api.attendancemgmt.property.FileStorageProperties;

@Service
public class FileServiceMobile {

	private static final Logger logger = LoggerFactory.getLogger(FileServiceMobile.class);

	// @Value("${android.app-dir}")
	private String fileBasePath = "D:/projectwork/FingerPrints";

	private final Path fileStorageLocation;

	@Autowired
	public FileServiceMobile() {
		this.fileStorageLocation = Paths.get(fileBasePath).toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
					ex);
		}
	}

	public String storeFile(MultipartFile file, String orgId) {
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		// Check If directory exist in the folder --> Folder Name should be OrgId

		File orgDir = new File(fileStorageLocation.toString() + "/" + orgId);
		try {
			if (orgDir.exists() && orgDir.isDirectory()) {
				logger.debug("Directory already exists, so file should be placed under it..");
				System.out.println("For Testing only, Please delete after");
			} else {
				Files.createDirectories(orgDir.toPath());
			}

			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = orgDir.toPath().resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			return fileName;

		} catch (IOException io) {
			logger.error("Error while creating directory" + io.getMessage());
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", io);
		} catch (Exception ep) {
			logger.error("Error while creating directory" + ep.getMessage());
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ep);
		}
	}

}
