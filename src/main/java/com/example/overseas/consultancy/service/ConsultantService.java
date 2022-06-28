package com.example.overseas.consultancy.service;

import java.util.Map;

public interface ConsultantService 
{

	public Map<String, Object> markAppoimentApprovedOrReject(Long id, String status);

	public Map<String, Object> getPendingAppoiment(Integer consultant_Id,String status);

	public Map<String, Object> getAllAppoimentList(Integer consultant_Id);

	public Map<String, Object>  getStudentProfileDetails(Integer id,String role);
		
}
