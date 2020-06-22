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
import com.asiczen.api.attendancemgmt.model.EmployeeDetails;
import com.asiczen.api.attendancemgmt.payload.request.EmployeeRequest;
import com.asiczen.api.attendancemgmt.payload.response.ApiResponse;
import com.asiczen.api.attendancemgmt.payload.response.EmpDeptCountResponse;
import com.asiczen.api.attendancemgmt.payload.response.EmployeeDepartmentResponse;
import com.asiczen.api.attendancemgmt.services.DeptServiceImpl;
import com.asiczen.api.attendancemgmt.services.EmpServiceImpl;
import com.asiczen.api.attendancemgmt.services.EmployeeDetailsServiceImpl;
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
	EmployeeDetailsServiceImpl empDetailsService;

	@Autowired
	ImageFileStorageService storageService;

	@Value("${emp.image.upload-dir}")
	private String fileBasePath;

	@Value("${emp.image.url}")
	private String imageUrl;

	@PostMapping("/emp")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<ApiResponse> createEmployee(@Valid @RequestBody EmployeeRequest employee) {

		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(HttpStatus.CREATED.value(),
				"Employee Created Successfully", empService.postEmployee(employee)));
	}

	/* Get all Employees */
	@GetMapping("/emp")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<ApiResponse> getAllEmployees() {
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
				"Employees extracted Successfully", empService.getAllEmployees()));
	}

	@GetMapping("/empbyorg")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<ApiResponse> getEmployeesByOrg(@Valid @RequestParam String orgId) {
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
				"employee by organization extracted successfully", empService.getEmployeeByOrganization(orgId)));
	}

	/* Update Employee */
	@PutMapping("/emp")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
	public ResponseEntity<ApiResponse> updateEmployee(@Valid @RequestBody EmployeeRequest emp) {
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
				"Employee updated Successfully", empService.updateEmployee(emp)));

	}

	/* Get Employee By Id */
	@GetMapping("/emp/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
	public ResponseEntity<ApiResponse> retrieveEmp(@Valid @PathVariable long id) {
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
				"Employee with id: " + id + " retrieved successfully", empService.getEmployeeById(id)));
	}

	@GetMapping("/emp/profile")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
	public ResponseEntity<ApiResponse> retrieveEmpbyEmail(@Valid @RequestParam String emailid) {
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
				"Employee with id: " + emailid + " retrieved successfully", empService.findByEmailid(emailid)));
	}

	/* Active Users */
	@GetMapping("/emp/count")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
	public ResponseEntity<ApiResponse> countEmpByOrganization(@Valid @RequestParam String orgid) {

		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
				"Employee Count extracted", empService.countEmployeebyOrganization(orgid, true)));
	}

	/* Returns count of both Emplyees and Departments by Organization */
	@GetMapping("/count")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
	public ResponseEntity<ApiResponse> countByOrganization(@Valid @RequestParam String orgid) {

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

		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
				"Emp with empId: " + empId + "found", empService.validateEmployee(empId.trim(), orgId.trim())));

	}

	@GetMapping("/emplist")
	public ResponseEntity<ApiResponse> getEmployeeByOrganization(@Valid @RequestParam String orgId) {

		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
				"Empoyees retrived successfully", empService.getEmpListbyOrg(orgId)));
	}

	@PostMapping("/emp/upload")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
	public ResponseEntity<ApiResponse> uploadLogo(@Valid @RequestParam("orgId") String orgId,
			@Valid @RequestParam("empId") String empId, @Valid @RequestParam("file") MultipartFile file) {

		Path fileStorageLocation = Paths.get(fileBasePath);
		String fileName = storageService.storeImage(file, orgId + "." + empId, fileStorageLocation);
		empService.updateImageUrl(orgId, empId, imageUrl + fileName);

		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponse(HttpStatus.OK.value(), "image uploaded successfully", fileName));
	}

	/* Data for Employee and Department dropdown */

	@GetMapping("/empdeptlist")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
	public ResponseEntity<ApiResponse> getEmployeeDepartmentByOrganization(@Valid @RequestParam String orgId) {

		List<String> empidList = empService.getEmpListbyOrg(orgId).getEmpId();
		List<String> deptList = new ArrayList<>();

		deptService.getDepartmentsByOrg(orgId).forEach(item -> {
			deptList.add(item.getDeptName());
		});

		EmployeeDepartmentResponse response = new EmployeeDepartmentResponse();

		response.setOrgId(orgId);
		response.setEmployeeList(empidList);
		response.setDepartmentList(deptList);

		return ResponseEntity.status(HttpStatus.OK).body(
				new ApiResponse(HttpStatus.OK.value(), "Employee Id and Deptnames extracted successfully", response));
	}

	/* Employee Salary Details Section */

	/* Update employee salary details. */
	@PostMapping("/empdetails")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<ApiResponse> postEmployeeSalaryDetails(@Valid @RequestBody EmployeeDetails empDetails) {

		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
				"Employee salary details posted successfully", empDetailsService.postEmployeeDetails(empDetails)));

	}

	// All Employee of Organizations
	@GetMapping("/empdetailsbyorg")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
	public ResponseEntity<ApiResponse> getEmployeeSalaryDetailsforOrg(@Valid @RequestParam String orgId) {

		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
				"Employee salary details extracted successfully", empDetailsService.getAllEmployeeDetails(orgId)));
	}

	// Get employee specific Details

	@GetMapping("/empdetails")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
	public ResponseEntity<ApiResponse> getEmployeeSalaryDetails(@Valid @RequestParam String orgId,
			@Valid @RequestParam String empId) {

		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
				"Employee salary details extracted successfully", empDetailsService.getEmployeeDetails(orgId, empId)));
	}

	// Update employee details
	@PutMapping("/updateemp")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<ApiResponse> updateEmpDetails(@Valid @RequestBody EmployeeDetails empDetails) {

		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
				"Employee salary details updated successfully", empDetailsService.updateEmployeeDetails(empDetails)));

	}

}
