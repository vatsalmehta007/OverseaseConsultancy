package com.example.overseas.consultancy.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.overseas.consultancy.Constant.ApplicationConstant;
import com.example.overseas.consultancy.Repository.AppoimentRepository;
import com.example.overseas.consultancy.Repository.ConsultantRepository;
import com.example.overseas.consultancy.entity.AppoimentDto;
import com.example.overseas.consultancy.entity.AppoimentEntity;
import com.example.overseas.consultancy.entity.UserEntity;
import com.example.overseas.consultancy.service.ConsultantService;
import com.example.overseas.consultancy.service.MailService;

@Service
public class ConsultantServiceImpl implements ConsultantService {
	public static final Logger logger = LoggerFactory.getLogger(ConsultantServiceImpl.class);

	@Autowired
	private ConsultantRepository consultantRepository;

	@Autowired
	private AppoimentRepository appoimentRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private MailService mailservice;

	@Override
	public Map<String, Object> markAppoimentApprovedOrReject(Long id, String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> mapObj = new HashMap<String, Object>();

		try {
			AppoimentEntity appoiment = appoimentRepository.getdata(id);
			if (appoiment != null) {

				LocalDateTime dateTime = LocalDateTime.now();

				appoimentRepository.updateappoiment(id, status, dateTime);

				mapObj.put("description", appoiment.getDescription());
				mapObj.put("status", appoiment.getStatus());
				mapObj.put("student_id", appoiment.getStudent_Id());
				mapObj.put("StartTime", appoiment.getStartTime());
				mapObj.put("EndTime", appoiment.getEndTime());
				map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_200);
				map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.APPOIMENT_APPROVED);
				map.put(ApplicationConstant.RESPONSE_DATA, mapObj);
			} else {
				map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_400);
				map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.SOMETING_WENT_WRONG);
			}
		} catch (Exception e) {
			logger.error(" while occure problem " + e.getMessage());
			map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.SOMETING_WENT_WRONG);
		}
		return map;
	}

	@Override
	public Map<String, Object> getPendingAppoiment(Integer consultant_Id, String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<AppoimentEntity> appoiment = appoimentRepository.GetByConsultantId(consultant_Id, status);
			List<AppoimentDto> appoimentList =new ArrayList<AppoimentDto>(); 
			String result = "pendding";

			for (AppoimentEntity appoimentEntity : appoiment) 
			{
				if (appoimentEntity.getStatus().equalsIgnoreCase(result)) 
				{
					AppoimentDto obj = new AppoimentDto();
					obj.setStudent_Id(appoimentEntity.getStudent_Id());
					obj.setConsultant_Id(appoimentEntity.getConsultant_Id());
					obj.setStartTime(appoimentEntity.getStartTime());
					obj.setEndTime(appoimentEntity.getEndTime());
					obj.setStatus(appoimentEntity.getStatus());
					obj.setDescription(appoimentEntity.getDescription());
					appoimentList.add(obj);

					map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_200);
					map.put(ApplicationConstant.RESPONSE_DATA, appoimentList);
					map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.USER_LIST_SUCCESS);
				} else {
					map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_400);
					map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.USER_NOT_FOUND);
				}
			}
		} catch (Exception e) {
			logger.error(" Problem occured while getPendingAppoiment , Please check logs  " + e.getMessage());
			map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.SOMETING_WENT_WRONG);
		}
		return map;
	}

	@Override
	public Map<String, Object> getAllAppoimentList(Integer consultant_Id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<AppoimentEntity> appoiment = appoimentRepository.FindByConsultantId(consultant_Id);
			List<AppoimentDto> appoimentList =new ArrayList<AppoimentDto>(); 
			if (!appoiment.isEmpty()) {
				
				for (AppoimentEntity appoimentEntity : appoiment) 
				{
					AppoimentDto obj=new AppoimentDto();
					obj.setStudent_Id(appoimentEntity.getStudent_Id());
					obj.setConsultant_Id(appoimentEntity.getConsultant_Id());
					obj.setStartTime(appoimentEntity.getStartTime());
					obj.setEndTime(appoimentEntity.getEndTime());
					obj.setStatus(appoimentEntity.getStatus());
					obj.setDescription(appoimentEntity.getDescription());
					appoimentList.add(obj);
				}
				
				
				map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_200);
				map.put(ApplicationConstant.RESPONSE_DATA, appoimentList);
				map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.APPOIMENT_LIST_SUCCESS);
			} else {
				map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_400);
				map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.USER_NOT_FOUND);
			}
		} catch (Exception e) {
			logger.error(" Problem occured while getAllAppoimentList , Please check logs  " + e.getMessage());
			map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.SOMETING_WENT_WRONG);
		}
		return map;
	}

	@Override
	public Map<String, Object> getStudentProfileDetails(Integer id, String role) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> mapobj = new HashMap<String, Object>();
		try {
			UserEntity userEntity = consultantRepository.findByUserId(id, role);

			if (userEntity != null) {
				mapobj.put("name", userEntity.getName());
				mapobj.put("email", userEntity.getEmail());
				mapobj.put("role", userEntity.getRole());
				mapobj.put("specification", userEntity.getSpecification());
				map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_200);
				map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.USER_SUCCESS);
				map.put(ApplicationConstant.RESPONSE_DATA, mapobj);
			} else {
				map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_400);
				map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.USER_NOT_FOUND);
			}
		} catch (Exception e) {
			logger.error("Problem occured while getStudentProfileDetails , Please check logs : ", e);
			map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.SOMETING_WENT_WRONG);
		}
		return map;
	}
}