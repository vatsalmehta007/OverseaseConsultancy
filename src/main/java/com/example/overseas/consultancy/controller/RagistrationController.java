package com.example.overseas.consultancy.controller;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.example.overseas.consultancy.entity.UserDto;
import com.example.overseas.consultancy.service.RagistrationService;

@RestController
@RequestMapping("/register")
public class RagistrationController {
	public static final Logger logger = LoggerFactory.getLogger(RagistrationController.class);

	@Autowired
	private RagistrationService ragistrationService;

	@GetMapping("/register")
	public String getRegistrationView() {

		return "registration";
	}

	@PostMapping("/registration")
	public ResponseEntity<Map<String, Object>> Registration(@RequestBody UserDto user,
			@RequestParam(value = "action") String action) {
		try {
			logger.info("Registration : ");
			return new ResponseEntity<>(ragistrationService.SaveUser(user, action), HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error occured while Registration user {} :Reason :{}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/verify")
	public ResponseEntity<Map<String, Object>> verification(@RequestParam(value = "email") String email,
			@RequestParam(value = "password") String password) {
		try {
			logger.info("verify user : ");
			return new ResponseEntity<>(ragistrationService.verification(email, password), HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error occured while verification user {} :Reason :{}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/forgetPassword")
	public ResponseEntity<Map<String, Object>> forgetPassword(@RequestParam(value = "email") String email) {
		try {
			logger.info("forgetPassword password  :");
			return new ResponseEntity<>(ragistrationService.forgetPassword(email), HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error occure while reset password {} :Reason :{}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/resetPassword")
	public ResponseEntity<Map<String, Object>> resetPassword(@RequestParam(value = "email") String email,
			@RequestParam(value = "password") String password,
			@RequestParam(value = "oldpassword") String oldPassword) {
		try {
			logger.info("reset password :");
			return new ResponseEntity<>(ragistrationService.resetPassword(email, password, oldPassword), HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error occure while reset password {} :Reason :{}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}
