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
import org.springframework.web.server.ResponseStatusException;

import com.example.overseas.consultancy.service.ConsultantService;

@RestController
@RequestMapping("/consultant")
public class ConsultantController {
	public static final Logger logger = LoggerFactory.getLogger(ConsultantController.class);

	@Autowired
	public ConsultantService consultantService;

	@PostMapping("/markAppoimentApprovedOrReject")
	public ResponseEntity<Map<String, Object>> markAppoimentApprovedOrReject(@RequestParam(value = "id") Long id,
			@RequestParam(value = "status") String status) {
		try {
			logger.info("mark Appoiment Approved Or Reject : ");
			return new ResponseEntity<>(consultantService.markAppoimentApprovedOrReject(id, status), HttpStatus.OK);
		} catch (Exception e) {

			logger.error("Error occured while mark Appoiment Approved Or Reject{} :Reason :{}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/getPendingAppoiment")
	public ResponseEntity<Map<String, Object>> getPendingAppoiment(
			@RequestParam(value = "consultant_Id") Integer consultant_Id,
			@RequestParam(value = "status") String status) {
		try {

			logger.info("get Pending Appoiment : ");
			return new ResponseEntity<>(consultantService.getPendingAppoiment(consultant_Id, status), HttpStatus.OK);

		} catch (Exception e) {

			logger.error("Error occured while getPendingAppoiment{} :Reason :{}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/getAllAppoimentList")
	public ResponseEntity<Map<String, Object>> getAllAppoimentList(
			@RequestParam(value = "consultant_Id") Integer consultant_Id) {
		try {
			logger.info("Get All Appoiment List : ");
			return new ResponseEntity<>(consultantService.getAllAppoimentList(consultant_Id), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error occured while getAllAppoimentList{} :Reason :{}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/getStudentProfileDetails")
	public ResponseEntity<Map<String, Object>> getStudentProfileDetails(@RequestParam(value = "student_id") Integer id,
			@RequestParam("role") String role) {
		try {
			logger.info("get Student Profile Details : ");
			return new ResponseEntity<>(consultantService.getStudentProfileDetails(id, role), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error occured while getStudentProfileDetails{} :Reason :{}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}