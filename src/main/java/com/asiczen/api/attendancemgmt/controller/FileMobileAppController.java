package com.asiczen.api.attendancemgmt.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.asiczen.api.attendancemgmt.payload.response.ApiResponse;
import com.asiczen.api.attendancemgmt.services.FileServiceMobile;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/file")
public class FileMobileAppController {

	private static final Logger logger = LoggerFactory.getLogger(FileMobileAppController.class);

	@Autowired
	private FileServiceMobile fileStorageService;
	
	@Value("${android.app-dir}")
	private String fileBasePath;

	@PostMapping("/upload")
	public ResponseEntity<ApiResponse> uploadToLocalFileSystem(@Valid @RequestParam("file") MultipartFile file,
			@Valid @RequestParam("orgId") String orgId) {
		
		Path fileStorageLocation = Paths.get(fileBasePath);
		
		String fileName = fileStorageService.storeFile(file, orgId,fileStorageLocation);

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(fileName).toUriString();

		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponse(HttpStatus.OK.value(), "File uploaded successfully.", fileDownloadUri));
	}

	@GetMapping("/download")
	public ResponseEntity<Resource> downloadFile(@RequestParam String orgId, HttpServletRequest request) {

		Path fileStorageLocation = Paths.get(fileBasePath);
		
		Resource resource = fileStorageService.loadFileAsResource(orgId,fileStorageLocation);
		
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			logger.error("Could not determine file type.");
		}

		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "application/octet-stream";
		}


		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

}
