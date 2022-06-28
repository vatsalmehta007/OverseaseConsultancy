package com.example.overseas.consultancy.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.example.overseas.consultancy.service.DocumentService;

@RestController
@RequestMapping("/document")
public class DocumentController {
	public static final Logger logger = LoggerFactory.getLogger(DocumentController.class);

	@Autowired
	private DocumentService documentservice;

	@PostMapping("/uplodeExcelData")
	public ResponseEntity<Map<String, Object>> uplodeExcelData(@RequestParam(value = "files") MultipartFile[] files) {
		try {
			logger.info("UploadExcelData :");
			return new ResponseEntity<>(documentservice.uplodeExcelData(files), HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error occured while UploadExcelData {} :Reason :{}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

	}

}
