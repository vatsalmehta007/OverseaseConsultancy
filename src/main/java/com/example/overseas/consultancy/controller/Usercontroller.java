package com.example.overseas.consultancy.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.example.overseas.consultancy.service.UserService;

@RestController
@RequestMapping("/user")
public class Usercontroller {
	public static final Logger logger = LoggerFactory.getLogger(Usercontroller.class);

	@Autowired
	public UserService userservice;

	@PostMapping("/uplodProfilePic")
	public ResponseEntity<Map<String, Object>> uplodProfilePic(@RequestParam(value = "id") Integer id,
			@RequestParam(value = "file") MultipartFile multipartFile) {
		try {
			logger.info("upload profile : ");
			return new ResponseEntity<>(userservice.uplodProfilePic(id, multipartFile), HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("Error occured while uplodProfilePic {} :Reason :{}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/getuserprofile")
	public ResponseEntity<Map<String, Object>> getuserprofile(@RequestParam(value = "id") Integer id) {
		try {
			logger.info("get profile picture : ");
			return new ResponseEntity<>(userservice.getuserprofile(id), HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error occured whilw getprofile {} :Reason :{}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/getrole")
	public ResponseEntity<Map<String, Object>> getrole(@RequestParam("role") String role) {
		try {
			logger.info("get by role : ");
			return new ResponseEntity<>(userservice.getrole(role), HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error occured whilw getprofile {} :Reason :{}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());

		}
	}

	@PostMapping("/editUserProfile")
	public ResponseEntity<Map<String, Object>> editUserprofile(@RequestParam(value = "id") Integer id,
			@RequestParam(value = "name") String name, @RequestParam(value = "email") String email,
			@RequestParam(value = "role") String role, @RequestParam(value = "specification") String specification) {
		try {
			logger.info("edit User Profile : ");
			return new ResponseEntity<>(userservice.editUserProfile(id, name, email, role, specification),
					HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error occured whilw editUserProfile {} :Reason :{}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());

		}
	}

	@GetMapping("/deleteuser")
	public ResponseEntity<Map<String, Object>> deleteuser(@RequestParam(value = "Id") Integer Id) {
		try {
			logger.info("Delete user : ");
			return new ResponseEntity<>(userservice.deleteuser(Id), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error occured whilw editUserProfile {} :Reason :{}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());

		}
	}

	@PostMapping("/updateUserName")
	public ResponseEntity<Map<String, Object>> updateUserName(@RequestParam(value = "id") Integer Id,
			@RequestParam(value = "name") String name) {
		try {
			logger.info("Update User Name : ");
			return new ResponseEntity<>(userservice.updateUserName(Id, name), HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error occured whilw updateUserName {} :Reason :{}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());

		}
	}

	@PostMapping("/editUserProfilePicture")
	public ResponseEntity<Map<String, Object>> editUserProfilePicture(@RequestParam(value = "id") Integer Id,
			@RequestParam(value = "file") MultipartFile multipartFile) {
		try {
			logger.info("Edit User Profile Picture : ");
			return new ResponseEntity<>(userservice.editUserProfilePicture(Id, multipartFile), HttpStatus.OK);

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error occured whilw updateUserName {} :Reason :{}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping("/setUserInactiveUserOrActive")
	public ResponseEntity<Map<String, Object>> setUserInactiveUserOrActive(@RequestParam(value = "id") Integer Id,
			@RequestParam(value = "action") String action, @RequestParam(value = "role") String role) {
		try {
			logger.info("Set Deactivate User : ");
			return new ResponseEntity<>(userservice.setUserInactiveUserOrActive(Id, action, role), HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error occured while Deactivate user {} :Reason :{}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping("/resatPasswordByOldpassword")
	public ResponseEntity<Map<String, Object>> resetpasswordByOldPassword(@RequestParam(value = "email") String email,
			@RequestParam(value = "old password") String oldpassword, @RequestParam String password) {
		try {
			logger.info("resat Password By Oldpassword : ");
			return new ResponseEntity<>(userservice.resatPasswordByOldpassword(email, oldpassword, password),
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error occured while resat Password By Oldpassword {} :Reason :{}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());

		}
	}
}
