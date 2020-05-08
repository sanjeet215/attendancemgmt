package com.asiczen.api.attendancemgmt.controller;


import javax.servlet.http.HttpServletRequest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.asiczen.api.attendancemgmt.model.Employee;
import com.asiczen.api.attendancemgmt.model.Organization;
import com.asiczen.api.attendancemgmt.repository.OrganizationRepository;
import com.asiczen.api.attendancemgmt.repository.RoleRepository;
import com.asiczen.api.attendancemgmt.repository.UserRepository;
import com.asiczen.api.attendancemgmt.security.jwt.JwtUtils;
import com.asiczen.api.attendancemgmt.services.EmpServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
	
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	OrganizationRepository orgRepo;
	
	@Autowired
	EmpServiceImpl employeeService;
	
	
	
	@GetMapping("/finduser")
	public String findUser() {
		
		 HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		 String token = request.getHeader("Authorization").split(" ")[1];
		
		 logger.debug("token is : "+ token);
		 
		 String username = jwtUtils.getUserNameFromJwtToken(token);
		 
		return token+"_"+username;
	}
	
	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public String userAccess() {
		return "User Content.";
	}

	@GetMapping("/mod")
	@PreAuthorize("hasRole('MODERATOR')")
	public String moderatorAccess() {
		return "Moderator Board.";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin Board.";
	}
	
	@GetMapping("/dummyOrg")
	public String createDummyOrg() {
		
		Organization org = new Organization();
		org.setContactEmailId("dummy@gmail.com");
		org.setContactPersonName("dummy");
		org.setOrganizationcontact("9999999999");
		org.setOrganizationDescription("dummy");
		org.setOrganizationLocation("India");
		org.setOrganizationDisplayName("dummy");
		org.setStatus("active");
		
		orgRepo.save(org);
		
		return "success";
		
		
		
	}
	
	@GetMapping("/dummyUser")
	public String createDummyUser() {
		Employee emp = new Employee();
		
		emp.setEmpEmailId("dummy@gmail.com");
		emp.setEmpId("dummy");
		emp.setOrgId("dummy");
		emp.setPhoneNo("9999999999");
		emp.setEmpLsatName("dummyLastName");
		emp.setEmpFirstName("dummyFirstName");
		emp.setEmpStatus(true);
		
		employeeService.addNewEmployee(emp);
		
		return "Employee Created";
	}
	
}

