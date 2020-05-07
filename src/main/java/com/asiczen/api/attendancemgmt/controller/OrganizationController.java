package com.asiczen.api.attendancemgmt.controller;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.asiczen.api.attendancemgmt.model.Organization;
import com.asiczen.api.attendancemgmt.payload.response.ApiResponse;
import com.asiczen.api.attendancemgmt.services.OrganizationServiceImpl;
import com.asiczen.api.attendancemgmt.utils.ImageFileStorageService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class OrganizationController {

	private static final Logger logger = LoggerFactory.getLogger(OrganizationController.class);

	@Autowired
	OrganizationServiceImpl orgService;

	@Value("${org.image.upload-dir}")
	private String fileBasePath;

	@Value("${org.image.url}")
	private String imageUrl;

	@Autowired
	ImageFileStorageService storageService;

	/* Create New Organization */

	@PostMapping("/org")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> createOrganization(@Valid @RequestBody Organization org) {

		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(HttpStatus.CREATED.value(),
				"Organization Created Successfully", orgService.addOrganization(org)));
	}

	/* Get all Organization */
	@GetMapping("/org")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> getAllOrganizations() {
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
				"Organization Extracted Successfully", orgService.getAllOrganization()));
	}

	/* Update Organization */
	@PutMapping("/org")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> updateOrganization(@Valid @RequestBody Organization org) {
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
				"Organization Updaed Successfully", orgService.updateOrganization(org)));

	}

	/* Mobile EndPoint */
	/* Check If Organiation Id is valid */

	@GetMapping("/org/validate")
	public ResponseEntity<ApiResponse> validateOrganization(@Valid @RequestParam String orgId) {

		if (logger.isDebugEnabled()) {
			logger.debug("Query parameter orgId-->" + orgId);
		}

		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
				"Organization " + orgId + " valiated", orgService.validateOrganization(orgId.trim())));
	}

	@PostMapping("/org/uploadlogo")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<ApiResponse> uploadLogo(@Valid @RequestParam("orgId") String orgId,
			@Valid @RequestParam("file") MultipartFile file) {

		Path fileStorageLocation = Paths.get(fileBasePath);
		String fileName = storageService.storeImage(file, orgId, fileStorageLocation);
		orgService.updateImage(orgId, imageUrl + fileName);

		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponse(HttpStatus.OK.value(), "image uploaded successfully", imageUrl + fileName));
	}
}
