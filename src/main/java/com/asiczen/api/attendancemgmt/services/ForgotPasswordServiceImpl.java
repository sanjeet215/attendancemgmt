package com.asiczen.api.attendancemgmt.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.asiczen.api.attendancemgmt.exception.ResourceNotFoundException;
import com.asiczen.api.attendancemgmt.model.Employee;
import com.asiczen.api.attendancemgmt.model.User;
import com.asiczen.api.attendancemgmt.payload.request.PasswordResetRequest;
import com.asiczen.api.attendancemgmt.repository.EmployeeRepository;
import com.asiczen.api.attendancemgmt.repository.UserRepository;
import com.asiczen.api.attendancemgmt.utils.RandomPwdGenerator;

@Service
public class ForgotPasswordServiceImpl {

	private static final Logger logger = LoggerFactory.getLogger(ForgotPasswordServiceImpl.class);
	
	@Value("${asiczen.from.email}")
	private String mailFrom;
	
	@Value("${asiczen.from.content}")
	private String mailcontent;
	
	@Value("${asiczen.from.subject}")
	private String mailsubject;
	

	@Autowired
	UserRepository userRepository;

	@Autowired
	EmployeeRepository empRepo;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	RandomPwdGenerator pwdGenerator;
	
	@Autowired
	EmailServiceImpl emailService;

	/*
	 * Password Reset Steps 1. Get Use Objects from Users table by email Id 2. Match
	 * the mobile number in Employee Table 3. If it matches Reset password 4. Else
	 * Throw error
	 */

	public String passwordReset(PasswordResetRequest request) {

		if (userRepository.existsByEmail(request.getEmailId()) && empRepo.existsByempEmailId(request.getEmailId())) {

			Employee emp = empRepo.findByempEmailId(request.getEmailId());
			if (emp.getPhoneNo().equalsIgnoreCase(request.getPhoneNo())) {
				logger.debug("Both email Id and Phone numbers are matching");

				User user = userRepository.findByEmail(request.getEmailId()).get();
				
				String randomPassword = pwdGenerator.generateRandomPassword();
				user.setPassword(encoder.encode(randomPassword));
				userRepository.save(user);
				//emailService.emailData(mailFrom, user.getEmail(), mailcontent+randomPassword, mailsubject);
				emailService.emailData(mailFrom, "sanjeet215@gmail.com", mailcontent+randomPassword, mailsubject);
				
				logger.info("Password Reset will be done.");
			}

			return "Password Reset Successful";
		} else {
			throw new ResourceNotFoundException("User Doesn't exist with emailId: " + request.getEmailId());
		}

	}

}
