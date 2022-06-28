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

import com.example.overseas.consultancy.entity.AppoimentEntity;
import com.example.overseas.consultancy.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {
	public static final Logger logger = LoggerFactory.getLogger(StudentController.class);

	@Autowired
	private StudentService studentService;

	@GetMapping("/getConsultantBySpecification")
	public ResponseEntity<Map<String, Object>> getConsultantBySpecification(
			@RequestParam(value = "specification") String specification, @RequestParam(value = "role") String role) {
		try {
			logger.info(" GetBySpecificaton : ");
			return new ResponseEntity<>(studentService.getConsultantBySpecification(specification, role),
					HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error occured while GetBySpecificaton {} :Reason :{}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/searchConsultant")
	public ResponseEntity<Map<String, Object>> searchConsultant(@RequestParam(value = "role") String role) {
		try {
			logger.info("Search consultant : ");
			return new ResponseEntity<>(studentService.searchConsultant(role), HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error occured while searchConsultant {} :Reason :{}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/getConsultantsProfileDetails")
	public ResponseEntity<Map<String, Object>> getConsultantsProfileDetails(@RequestParam(value = "id") Integer id,
			@RequestParam("role") String role) {
		try {
			logger.info("Get Consultants ProfileDetails");
			return new ResponseEntity<>(studentService.getConsultantsProfileDetails(id, role), HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error occured while getConsultantsProfileDetails {} :Reason :{}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping("/saveAppoiment")
	public ResponseEntity<Map<String, Object>> saveAppoiment(@RequestBody AppoimentEntity appoiment) {
		try {
			logger.info("Sent Appoiment To Consultants :");
			return new ResponseEntity<>(studentService.saveAppoiment(appoiment), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while saveAppoiment {} :Reson :{}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/getApproveOrPenndingAppoiment")
	public ResponseEntity<Map<String, Object>> getApproveOrPenndingAppoiment(
			@RequestParam(value = "student_Id") Integer student_Id) {
		try {
			logger.info("check Appoiment Approve or pennding : ");
			return new ResponseEntity<>(studentService.getApproveOrPenndingAppoiment(student_Id), HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error occured while saveAppoiment {} :Reson :{}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/getAllConsultantList")
	public ResponseEntity<Map<String, Object>> getAllConsultantList(@RequestParam(value = "role") String role) {
		try {
			logger.info("Get All Consultant List : ");
			return new ResponseEntity<>(studentService.getAllConsultantList(role), HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error occured while saveAppoiment {} :Reson :{}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());

		}
	}
}
