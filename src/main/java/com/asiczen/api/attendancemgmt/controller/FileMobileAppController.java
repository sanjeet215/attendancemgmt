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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.asiczen.api.attendancemgmt.model.LockDetails;
import com.asiczen.api.attendancemgmt.payload.request.EmployeeSwipeRequest;
import com.asiczen.api.attendancemgmt.payload.response.ApiResponse;
import com.asiczen.api.attendancemgmt.services.EmpinoutServicImpl;
import com.asiczen.api.attendancemgmt.services.FileServiceMobile;
import com.asiczen.api.attendancemgmt.services.LockService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/file")
public class FileMobileAppController {

	private static final Logger logger = LoggerFactory.getLogger(FileMobileAppController.class);

	@Autowired
	private FileServiceMobile fileStorageService;

	@Autowired
	private EmpinoutServicImpl empLogService;

	@Autowired
	LockService lockService;

	@Value("${android.app-dir}")
	private String fileBasePath;

	@PostMapping("/upload")
	public ResponseEntity<ApiResponse> uploadToLocalFileSystem(@Valid @RequestParam("file") MultipartFile file,
			@Valid @RequestParam("orgId") String orgId) {

		Path fileStorageLocation = Paths.get(fileBasePath);

		String fileName = fileStorageService.storeFile(file, orgId, fileStorageLocation);

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(fileName).toUriString();

		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponse(HttpStatus.OK.value(), "File uploaded successfully.", fileDownloadUri));
	}

	@GetMapping("/download")
	public ResponseEntity<Resource> downloadFile(@RequestParam String orgId, HttpServletRequest request) {

		Path fileStorageLocation = Paths.get(fileBasePath);

		Resource resource = fileStorageService.loadFileAsResource(orgId, fileStorageLocation);

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

	@DeleteMapping("/delete")
	public ResponseEntity<ApiResponse> deleteFile(@Valid @RequestParam String orgId,
			@Valid @RequestParam String fileName) {

		Path fileStorageLocation = Paths.get(fileBasePath);
		try {
			fileStorageService.deleteFile(orgId, fileName, fileStorageLocation);
		} catch (IOException e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
							"File " + fileName + " can't be removed", e.getLocalizedMessage()));
		}

		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
				"File deleted successfully", "File " + fileName + " removed successfully"));
	}

	@PostMapping("/empinouttime")
	public ResponseEntity<ApiResponse> captureLoginLogout(@Valid @RequestBody EmployeeSwipeRequest request) {

		logger.info(request.toString());

		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(HttpStatus.CREATED.value(),
				"Record Posted Successfully", empLogService.postEmpLoginDetails(request)));
	}

	@GetMapping("/empinouttime")
	public ResponseEntity<ApiResponse> getLoginLogoutDetails(@Valid @RequestParam String orgId,
			@Valid @RequestParam String empId) {

		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.CREATED.value(),
				"Records extracted successfully", empLogService.getEmpLoginDetails(orgId, empId)));
	}

	@PostMapping("/savelock")
	public ResponseEntity<ApiResponse> postData(@Valid @RequestBody LockDetails lockDetails) {

		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(HttpStatus.CREATED.value(),
				"Record extracted successfully", lockService.postDataToRepo(lockDetails)));
	}

	@GetMapping("/getlock")
	public ResponseEntity<ApiResponse> getDatabyEmpid(@Valid @RequestParam String email) {

		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.CREATED.value(),
				"Records extracted successfully", lockService.getMacidsbyEmailId(email)));

	}

}
