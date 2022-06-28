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
import com.example.overseas.consultancy.Repository.StudentRepository;
import com.example.overseas.consultancy.entity.AppoimentDto;
import com.example.overseas.consultancy.entity.AppoimentEntity;
import com.example.overseas.consultancy.entity.UserDto;
import com.example.overseas.consultancy.entity.UserEntity;
import com.example.overseas.consultancy.service.MailService;
import com.example.overseas.consultancy.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {
	public static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private AppoimentRepository appoimentRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private MailService mailservice;

	@Override
	public Map<String, Object> getConsultantBySpecification(String specification, String role) {

		Map<String, Object> map = new HashMap<String, Object>();

		try {
			List<UserEntity> userEnt = studentRepository.FindByspecification(specification, role);

			if (!userEnt.isEmpty()) {
				List<UserDto> userList = new ArrayList<UserDto>();
				UserDto userDto = new UserDto();
				for (UserEntity userEntity : userEnt) {
					userDto.setEmail(userEntity.getEmail());
					userDto.setName(userEntity.getName());
					userDto.setRole(userEntity.getRole());
					userDto.setSpecification(userEntity.getSpecification());
					userDto.setPassword("*******");
					userList.add(userDto);
				}
				map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_200);
				map.put(ApplicationConstant.RESPONSE_DATA, userList);
				map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.USER_LIST_SUCCESS);
			} else {
				map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_400);
				map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.USER_NOT_FOUND);
			}
		} catch (Exception e) {

			logger.error("Problem occured while getConsultantBySpecification , Please check logs : " + e.getMessage());
			map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.SOMETING_WENT_WRONG);
		}
		return map;
	}

	@Override
	public Map<String, Object> searchConsultant(String role) {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<UserEntity> userEntity = studentRepository.FindByRole(role);
			List<UserDto> userDtObj = new ArrayList<UserDto>();

			if (!userEntity.isEmpty()) {

				for (UserEntity user : userEntity) {
					UserDto userDto = new UserDto();
					userDto.setEmail(user.getEmail());
					userDto.setName(user.getName());
					userDto.setRole(user.getRole());
					userDto.setSpecification(user.getSpecification());
					userDto.setPassword("*******");
					userDtObj.add(userDto);
				}

				map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_200);
				map.put(ApplicationConstant.RESPONSE_DATA, userDtObj);
				map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.USER_LIST_SUCCESS);
			} else {
				map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_400);
				map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.USER_NOT_FOUND);
			}
		} catch (Exception e) {

			logger.error("Problem occured while searchConsultant , Please check logs : " + e.getMessage());
			map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.SOMETING_WENT_WRONG);
		}
		return map;
	}

	@Override
	public Map<String, Object> getConsultantsProfileDetails(Integer id, String role) {

		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> mapobj = new HashMap<String, Object>();

		try {
			UserEntity userEntity = studentRepository.findById(id, role);
			if (userEntity != null) {
				mapobj.put("name", userEntity.getName());
				mapobj.put("email", userEntity.getEmail());
				mapobj.put("role", userEntity.getRole());
				mapobj.put("specification", userEntity.getSpecification());

				map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_200);
				map.put(ApplicationConstant.RESPONSE_DATA, mapobj);
				map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.USER_SUCCESS);
			} else {
				map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_400);
				map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.USER_NOT_FOUND);
			}
		} catch (Exception e) {
			logger.error("Problem occured while getConsultantsProfileDetails , Please check logs : " + e.getMessage());
			map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.SOMETING_WENT_WRONG);
		}
		return map;
	}

	@Override
	public Map<String, Object> saveAppoiment(final AppoimentEntity appoiment) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> mapObj = new HashMap<String, Object>();
		try {

			if (appoiment.getConsultant_Id() != null) {
				studentRepository.save(populatedata(appoiment));

				mapObj.put("description", appoiment.getDescription());
				mapObj.put("status", "pendding");
				mapObj.put("consultant_id", appoiment.getConsultant_Id());
				mapObj.put("student_id", appoiment.getStudent_Id());
				mapObj.put("StartTime", appoiment.getStartTime());
				mapObj.put("EndTime", appoiment.getEndTime());

				map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_200);
				map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.APPOIMENT_SUCCESS);
				map.put(ApplicationConstant.RESPONSE_DATA, mapObj);
			} else {
				map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_400);
				map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.APPOIMENT_FAIL);
			}
		} catch (Exception e) {
			logger.info("Problem occured while saveAppoiment , Please check logs : " + e.getMessage());
			map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.SOMETING_WENT_WRONG);
		}
		return map;
	}

	private AppoimentEntity populatedata(final AppoimentEntity appoiment) {
		AppoimentEntity appoiment2 = new AppoimentEntity();
		appoiment2.setCreated_on(LocalDateTime.now());
		appoiment2.setModified_on(LocalDateTime.now());
		appoiment2.setConsultant_Id(appoiment.getConsultant_Id());
		appoiment2.setStudent_Id(appoiment.getStudent_Id());
		appoiment2.setStatus("pendding");
		appoiment2.setDescription(appoiment.getDescription());
		appoiment2.setStartTime(appoiment.getStartTime());
		appoiment2.setEndTime(appoiment.getEndTime());
		return appoiment2;
	}

	@Override
	public Map<String, Object> getApproveOrPenndingAppoiment(Integer student_Id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<AppoimentEntity> appoiment = appoimentRepository.findByStudentId(student_Id);
			List<AppoimentDto> appoimentList = new ArrayList<AppoimentDto>();

			if (!appoiment.isEmpty()) {
				for (AppoimentEntity appoimentEntity : appoiment) {
					if (!appoimentEntity.getStatus().equals("rejected")) {

						AppoimentDto obj = new AppoimentDto();
						obj.setStudent_Id(appoimentEntity.getStudent_Id());
						obj.setConsultant_Id(appoimentEntity.getConsultant_Id());
						obj.setStartTime(appoimentEntity.getStartTime());
						obj.setEndTime(appoimentEntity.getEndTime());
						obj.setStatus(appoimentEntity.getStatus());
						obj.setDescription(appoimentEntity.getDescription());
						appoimentList.add(obj);

						map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_200);
						map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.APPOIMENT_LIST_SUCCESS);
						map.put(ApplicationConstant.RESPONSE_DATA, appoimentList);
					} else {
						map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_400);
						map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.APPOIMENT_REJECTED);
					}
				}
			} else {
				map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_400);
				map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.USER_NOT_FOUND);
			}
		} catch (Exception e) {
			logger.error("Problem occured while getApproveOrPenndingAppoiment , Please check logs : " + e.getMessage());
			map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.SOMETING_WENT_WRONG);
		}
		return map;
	}

	@Override
	public Map<String, Object> getAllConsultantList(String role) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<UserEntity> user1 = studentRepository.FindByRole(role);
			List<UserDto> user2 = new ArrayList<UserDto>();

			if (!user1.isEmpty()) {
				for (UserEntity userEn : user1) {
					UserDto userDto = new UserDto();
					userDto.setEmail(userEn.getEmail());
					userDto.setName(userEn.getName());
					userDto.setRole(userEn.getRole());
					userDto.setSpecification(userEn.getSpecification());
					userDto.setPassword("*******");
					user2.add(userDto);
				}
				map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_200);
				map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.USER_LIST_SUCCESS);
				map.put(ApplicationConstant.RESPONSE_DATA, user2);
			} else {
				map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_400);
				map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.USER_NOT_FOUND);
			}
		} catch (Exception e) {
			logger.error(" Problem occured while getAllConsultantList , Please check logs  " + e.getMessage());
			map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.SOMETING_WENT_WRONG);
		}
		return map;
	}
}
