package com.asiczen.api.attendancemgmt.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.asiczen.api.attendancemgmt.payload.response.ApiResponse;
import com.asiczen.api.attendancemgmt.payload.response.UploadFileResponse;
import com.asiczen.api.attendancemgmt.services.FileServiceMobile;
import com.asiczen.api.attendancemgmt.services.FileStorageService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/file")
public class FileMobileAppController {
	
	private static final Logger logger = LoggerFactory.getLogger(FileMobileAppController.class);
	
	@Autowired
	private FileServiceMobile fileStorageService;
	
	
	
	
	@PostMapping("/upload")
	public ResponseEntity<ApiResponse> uploadToLocalFileSystem(@Valid @RequestParam("file") MultipartFile file,
												  @Valid @RequestParam("orgId") String orgId) 
	{
		String fileName = fileStorageService.storeFile(file,orgId);
		
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(fileName).toUriString();
		
		
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
																		 "File uploaded successfully.",
																		 fileDownloadUri));
		
//		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//		Path path = Paths.get(fileBasePath + fileName);
//		try {
//			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//				.path("/files/download/")
//				.path(fileName)
//				.toUriString();
//		return ResponseEntity.ok(fileDownloadUri);
		
	}
	
//	@GetMapping("/downloadFile/{orgId:.+}")
//	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
//		
//	}

}
