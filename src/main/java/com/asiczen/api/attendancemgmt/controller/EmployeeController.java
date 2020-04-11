package com.asiczen.api.attendancemgmt.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.asiczen.api.attendancemgmt.model.Employee;
import com.asiczen.api.attendancemgmt.payload.response.ApiResponse;
import com.asiczen.api.attendancemgmt.payload.response.EmpDeptCountResponse;
import com.asiczen.api.attendancemgmt.services.DeptServiceImpl;
import com.asiczen.api.attendancemgmt.services.EmpServiceImpl;
import com.asiczen.api.attendancemgmt.services.OrganizationServiceImpl;
import com.asiczen.api.attendancemgmt.utils.ImageFileStorageService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class EmployeeController {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	EmpServiceImpl empService;

	@Autowired
	DeptServiceImpl deptService;

	@Autowired
	OrganizationServiceImpl orgService;

	@Autowired
	ImageFileStorageService storageService;

	@Value("${emp.image.upload-dir}")
	private String fileBasePath;

	/* Create New Employee */

	@PostMapping("/emp")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<ApiResponse> createEmployee(@Valid @RequestBody Employee emp) {

		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(HttpStatus.CREATED.value(),
				"Employee Created Successfully", empService.addNewEmployee(emp)));
	}

	/* Get all Employees */
	@GetMapping("/emp")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<ApiResponse> getAllEmployees() {
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
				"Organization Created Successfully", empService.getAllEmployees()));
	}

	/* Update Employee */
	@PutMapping("/emp")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<ApiResponse> updateEmployee(@Valid @RequestBody Employee emp) {
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
				"Organization Created Successfully", empService.updateEmployee(emp)));

	}

	/* Get Employee By Id */
	@GetMapping("/emp/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('user')")
	public ResponseEntity<ApiResponse> retrieveEmp(@Valid @PathVariable long id) {
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
				"Employee with id: " + id + " retrieved successfully", empService.getEmployeeById(id)));
	}

	@GetMapping("/emp/profile")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('user')")
	public ResponseEntity<ApiResponse> retrieveEmpbyEmail(@Valid @RequestParam String emailid) {
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
				"Employee with id: " + emailid + " retrieved successfully", empService.findByEmailid(emailid)));
	}

	/* Active Users */
	@GetMapping("/emp/count")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('user')")
	public ResponseEntity<ApiResponse> countEmpByOrganization(@Valid @RequestParam String orgid) {
		logger.debug("Incoming Organization id: " + orgid);

		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
				"Employee Count extracted", empService.countEmployeebyOrganization(orgid, true)));
	}

	/* Returns count of both Emplyees and Departments by Organization */
	@GetMapping("/count")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('user')")
	public ResponseEntity<ApiResponse> countByOrganization(@Valid @RequestParam String orgid) {
		logger.debug("Incoming Organization id: " + orgid);

		List<EmpDeptCountResponse> count = new ArrayList<EmpDeptCountResponse>();

		try {
			EmpDeptCountResponse empResponse = new EmpDeptCountResponse();
			empResponse.setCountType("EmpCount");
			empResponse.setCount(empService.countEmployeebyOrganization(orgid, true));
			count.add(empResponse);

			EmpDeptCountResponse deptResponse = new EmpDeptCountResponse();
			deptResponse.setCountType("DeptCount");
			deptResponse.setCount(deptService.countDepartmentbyOrg(orgid));
			count.add(deptResponse);

		} catch (Exception ep) {
			logger.error("Error in gettting the count");
		}

		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponse(HttpStatus.OK.value(), "Emp/Dept Count extracted", count));
	}

	/* Returns count of both Emplyees and Departments for Organization owner */

	@GetMapping("/allcount")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> countByAllResources() {

		List<EmpDeptCountResponse> count = new ArrayList<>();

		try {
			EmpDeptCountResponse empResponse = new EmpDeptCountResponse();
			empResponse.setCountType("EmpCount");
			empResponse.setCount(empService.countEmployee());
			count.add(empResponse);

			EmpDeptCountResponse orgnizationCount = new EmpDeptCountResponse();
			orgnizationCount.setCountType("OrgCount");
			orgnizationCount.setCount(orgService.countOrganization());
			count.add(orgnizationCount);
			
		} catch (Exception ep) {
			logger.error("Error while extracting the count" + ep.getLocalizedMessage());
		}

		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponse(HttpStatus.OK.value(), "Emp/Dept Count extracted", count));
	}
	

	@GetMapping("/emp/validate")
	public ResponseEntity<ApiResponse> validateEmployee(@Valid @RequestParam String empId,
			@Valid @RequestParam String orgId) {
		logger.debug("Query parameter empId--> " + empId);

		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
				"Emp with empId: " + empId + "found", empService.validateEmployee(empId.trim(), orgId.trim())));

	}

	@GetMapping("/emplist")
	public ResponseEntity<ApiResponse> getEmployeeByOrganization(@Valid @RequestParam String orgId) {

		logger.debug("Query parameter -->" + orgId);

		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
				"Empoyees retrived successfully", empService.getEmpListbyOrg(orgId)));
	}

	@PostMapping("/emp/upload")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<ApiResponse> uploadLogo(@Valid @RequestParam("orgId") String orgId,
			@Valid @RequestParam("empId") String empId, @Valid @RequestParam("file") MultipartFile file) {

		Path fileStorageLocation = Paths.get(fileBasePath);
		storageService.storeFile(file, orgId, fileStorageLocation);

		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponse(HttpStatus.OK.value(), "image uploaded successfully",
						storageService.storeFile(file, orgId + "." + empId, fileStorageLocation)));
	}
}
