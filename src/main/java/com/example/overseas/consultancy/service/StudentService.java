package com.example.overseas.consultancy.service;

import java.util.Map;

import com.example.overseas.consultancy.entity.AppoimentEntity;

public interface StudentService 
{
	public Map<String, Object> getConsultantBySpecification(String specification,String role);

	public Map<String, Object> searchConsultant(String role);

    public Map<String, Object> getConsultantsProfileDetails(Integer id, String role);

    public Map<String, Object> saveAppoiment(AppoimentEntity appoiment);

    public Map<String, Object> getApproveOrPenndingAppoiment(Integer student_Id);

    public Map<String, Object> getAllConsultantList(String role);

}
