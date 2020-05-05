package com.asiczen.api.attendancemgmt.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiczen.api.attendancemgmt.exception.ResourceNotFoundException;
import com.asiczen.api.attendancemgmt.exception.UnauthorizedAccess;
import com.asiczen.api.attendancemgmt.model.ERole;
import com.asiczen.api.attendancemgmt.model.Employee;
import com.asiczen.api.attendancemgmt.model.Role;
import com.asiczen.api.attendancemgmt.model.User;
import com.asiczen.api.attendancemgmt.payload.request.LoginRequest;
import com.asiczen.api.attendancemgmt.payload.request.UserRoleChangeRequest;
import com.asiczen.api.attendancemgmt.repository.RoleRepository;
import com.asiczen.api.attendancemgmt.repository.UserRepository;

@Service
public class AuthService {

	private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	EmpServiceImpl employeeService;

	public User updatedUser(UserRoleChangeRequest request) {

		Optional<User> userdb = userRepository.findByOrgIdAndEmail(request.getOrgId(), request.getEmail());

		if (!userdb.isPresent()) {
			throw new ResourceNotFoundException("Resouce not found with orgid and emailid combination");
		}

		User user = userdb.get();

		Set<String> strRoles = request.getRole();
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
		return userRepository.save(user);
	}

	public boolean validateUser(LoginRequest request) {

		User user = userRepository.findByUsername(request.getUsername())
				.orElseThrow(() -> new UnauthorizedAccess("Unauthorized access"));

		Employee emp = employeeService.findByEmailid(user.getEmail());
		
		if(emp.isEmpStatus()) {
			throw new UnauthorizedAccess("Account has been disabled.");
		} else {
			return true;
		}
	}
}
