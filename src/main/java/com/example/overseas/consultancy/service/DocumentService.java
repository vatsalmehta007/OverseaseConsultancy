package com.example.overseas.consultancy.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface DocumentService 
{

	public Map<String, Object> uplodeExcelData(MultipartFile[] files);

}
