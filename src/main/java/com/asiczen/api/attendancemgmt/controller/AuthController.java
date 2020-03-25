package com.asiczen.api.attendancemgmt.controller;


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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asiczen.api.attendancemgmt.exception.ResourceAlreadyExistException;
import com.asiczen.api.attendancemgmt.model.ERole;
import com.asiczen.api.attendancemgmt.model.Role;
import com.asiczen.api.attendancemgmt.model.User;
import com.asiczen.api.attendancemgmt.payload.request.LoginRequest;
import com.asiczen.api.attendancemgmt.payload.request.SignupRequest;
import com.asiczen.api.attendancemgmt.payload.response.ApiResponse;
import com.asiczen.api.attendancemgmt.payload.response.JwtResponse;
import com.asiczen.api.attendancemgmt.repository.RoleRepository;
import com.asiczen.api.attendancemgmt.repository.UserRepository;
import com.asiczen.api.attendancemgmt.security.jwt.JwtUtils;
import com.asiczen.api.attendancemgmt.services.EmailServiceImpl;
import com.asiczen.api.attendancemgmt.services.UserDetailsImpl;




@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private static final Logger logger= LoggerFactory.getLogger(AuthController.class);
	
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
	
//	@Autowired
//	ModeratorRepository modRepo;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		logger.debug("Ching for Orgnization id : "+userDetails.toString());
		System.out.println("Printing value");
		logger.info("Ching for Orgnization id : "+userDetails.toString());
		
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
																		 "User validate Successfully",
																		 new JwtResponse(jwt, 
																				 userDetails.getId(), 
																				 userDetails.getUsername(), 
																				 userDetails.getEmail(), 
																				 roles,
																				 userDetails.getOrgId())));	
	}

	@PostMapping("/signup")
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			throw new ResourceAlreadyExistException("Error: UserId is already in taken!");
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			throw new ResourceAlreadyExistException("Error: Email is already in use!");
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(), 
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()),
							 signUpRequest.getOrgId());

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
		//userRepository.save(user);
		//return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
		
		String credentials = "User Name: "+user.getUsername()+ "\nPassword: "+signUpRequest.getPassword();
		
//		roles.forEach(item->{
//			logger.info("User Role"+item.getName().toString());
//			if(item.getName().toString().contains("ROLE_USER")) {
//				
//			} else {
//				modRepo.save(new Moderator(user.getOrgId(),user.getEmail(),user.getUsername(),true));
//			}
//		});
		
		
		
		emailService.emailData(mailFrom,user.getEmail(), mailcontent+credentials, "User registered successfully!");
		
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(HttpStatus.CREATED.value(),
															  				  "User registered successfully!",
															  				  userRepository.save(user)));
	}
	
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAllUser(){
		//return ResponseEntity.ok(userRepository.findAll());
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
																		 "All users are extracted successfully",
																		 userRepository.findAll()));
	}
	
}

