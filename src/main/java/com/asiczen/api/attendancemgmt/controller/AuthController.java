package com.asiczen.api.attendancemgmt.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asiczen.api.attendancemgmt.exception.ResourceAlreadyExistException;
import com.asiczen.api.attendancemgmt.exception.ResourceNotFoundException;
import com.asiczen.api.attendancemgmt.model.ERole;
import com.asiczen.api.attendancemgmt.model.Employee;
import com.asiczen.api.attendancemgmt.model.Role;
import com.asiczen.api.attendancemgmt.model.User;
import com.asiczen.api.attendancemgmt.payload.request.LoginRequest;
import com.asiczen.api.attendancemgmt.payload.request.SignupRequest;
import com.asiczen.api.attendancemgmt.payload.request.UserRoleChangeRequest;
import com.asiczen.api.attendancemgmt.payload.response.ApiResponse;
import com.asiczen.api.attendancemgmt.payload.response.JwtResponse;
import com.asiczen.api.attendancemgmt.payload.response.UserByOrganizationResponse;
import com.asiczen.api.attendancemgmt.repository.RoleRepository;
import com.asiczen.api.attendancemgmt.repository.UserRepository;
import com.asiczen.api.attendancemgmt.security.jwt.JwtUtils;
import com.asiczen.api.attendancemgmt.services.AuthService;
import com.asiczen.api.attendancemgmt.services.EmailServiceImpl;
import com.asiczen.api.attendancemgmt.services.EmpServiceImpl;
import com.asiczen.api.attendancemgmt.services.OrganizationServiceImpl;
import com.asiczen.api.attendancemgmt.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Value("${asiczen.from.email}")
	private String mailFrom;

	@Value("${asiczen.from.userregn}")
	private String mailcontent;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	EmailServiceImpl emailService;

	@Autowired
	EmpServiceImpl employeeService;

	@Autowired
	AuthService authService;

	@Autowired
	OrganizationServiceImpl orgService;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		/*
		 * Check if Employee is active or In active from userName. then allow to login
		 * or disallow
		 */
		authService.validateUser(loginRequest);

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponse(HttpStatus.OK.value(), "User validate Successfully",
						new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(),
								roles, userDetails.getOrgId(),
								orgService.getOrganizationByid(userDetails.getOrgId()).getOrgLogo())));
	}

	@PostMapping("/signup")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

		if (Boolean.TRUE.equals(userRepository.existsByUsername(signUpRequest.getUsername()))) {
			throw new ResourceAlreadyExistException("Error: UserId is already in taken!");
		}

		if (Boolean.TRUE.equals(userRepository.existsByEmail(signUpRequest.getEmail()))) {
			throw new ResourceAlreadyExistException("Error: Email is already in use!");
		}

		employeeService.validateEmp(signUpRequest.getPhoneNo(), signUpRequest.getEmpId(), signUpRequest.getOrgId());

		// Create new user's account
		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()), signUpRequest.getOrgId());

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		String credentials = "User Name: " + user.getUsername() + "\nPassword: " + signUpRequest.getPassword();

		emailService.emailData(mailFrom, user.getEmail(), mailcontent + credentials, "User registered successfully!");

		/*
		 * Section is to populate employee Details with minimum data
		 */
		Employee emp = new Employee();

		emp.setEmpEmailId(signUpRequest.getEmail());
		emp.setEmpFirstName(signUpRequest.getUsername());
		emp.setEmpId(signUpRequest.getEmpId());
		emp.setOrgId(signUpRequest.getOrgId());
		emp.setPhoneNo(signUpRequest.getPhoneNo());
		emp.setEmpLsatName("LastName");
		emp.setEmpStatus(true);
		employeeService.addNewEmployee(emp);

		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(HttpStatus.CREATED.value(),
				"User registered successfully!", userRepository.save(user)));
	}

	@GetMapping("/user")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAllUser() {
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
				"All users are extracted successfully", userRepository.findAll()));
	}

	@PutMapping("/user")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<ApiResponse> updateUser(@Valid @RequestBody UserRoleChangeRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
				"User role updated successfully", authService.updatedUser(request)));
	}

	@GetMapping("/userbyorg")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<ApiResponse> getusersByOrganization(@Valid @RequestParam String orgId) {

		List<UserByOrganizationResponse> responseList = new ArrayList<>();
		List<User> userList = userRepository.findByorgId(orgId)
				.orElseThrow(() -> new ResourceNotFoundException("No employees registered for the organization "));

		userList.forEach(item -> {
			UserByOrganizationResponse response = new UserByOrganizationResponse();

			response.setId(item.getId());
			response.setEmail(item.getEmail());
			response.setUsername(item.getUsername());
			response.setRoles(item.getRoles());
			response.setOrgId(item.getOrgId());
			response.setEmpId(employeeService.findByEmailid(item.getEmail()).getEmpId());

			responseList.add(response);
		});

		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponse(HttpStatus.OK.value(), "Users extracted for organization", responseList));

	}

}
