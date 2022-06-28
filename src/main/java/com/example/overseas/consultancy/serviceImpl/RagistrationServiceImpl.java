package com.example.overseas.consultancy.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.overseas.consultancy.Constant.ApplicationConstant;
import com.example.overseas.consultancy.Repository.RagistrationRepository;
import com.example.overseas.consultancy.entity.UserDto;
import com.example.overseas.consultancy.entity.UserEntity;
import com.example.overseas.consultancy.service.MailService;
import com.example.overseas.consultancy.service.RagistrationService;

@Service
public class RagistrationServiceImpl implements RagistrationService {

	public static final Logger logger = LoggerFactory.getLogger(RagistrationServiceImpl.class);

	@Autowired
	private RagistrationRepository ragistrationRepository;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private MailService mailservice;

	@Override
	public Map<String, Object> SaveUser(final UserDto user, String action) {
	
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (action.equalsIgnoreCase("add")) {

				Optional<UserEntity> userEntity = ragistrationRepository.findOneByEmailIgnoreCase(user.getEmail());
				System.out.println(userEntity);
				if (!userEntity.isPresent()) {

					ragistrationRepository.save(populateUser(user));
					mailservice.sendwelcomemail(user);

					map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_200);
					map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.REGISTRATION_SUCCESS);
					map.put(ApplicationConstant.RESPONSE_DATA, new ArrayList<>());
					return map;
				} else {
					map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_400);
					map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.REGISTRATION_EMAIL_EXISTS);
					map.put(ApplicationConstant.RESPONSE_DATA, new ArrayList<>());
					return map;
				}
			} else if (action.equalsIgnoreCase("addByAdmin")) {
				Optional<UserEntity> userEntity = ragistrationRepository.findOneByEmailIgnoreCase(user.getEmail());

				if (!userEntity.isPresent()) {
					UserEntity userModel = populateUser(user);
					userModel.setIsVerified(true);
					ragistrationRepository.save(userModel);
					map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_200);
					map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.REGISTRATION_SUCCESS);
					map.put(ApplicationConstant.RESPONSE_DATA, new ArrayList<>());
					return map;
				} else {
					map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_400);
					map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.REGISTRATION_EMAIL_EXISTS);
					map.put(ApplicationConstant.RESPONSE_DATA, new ArrayList<>());
					return map;
				}
			} else {
				return map;
			}
		} catch (Exception e) {
			logger.error(" Problem occured while Ragistration , Please check logs  " + e);
			map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.SOMETING_WENT_WRONG);
		}
		return map;
	}

	public UserEntity populateUser(final UserDto user) {
		UserEntity userobj = new UserEntity();
		userobj.setName(user.getName());
		userobj.setPassword(passwordEncoder.encode(user.getPassword()));
		userobj.setSpecification(user.getSpecification());
		userobj.setRole(user.getRole());
		userobj.setEmail(user.getEmail());
		userobj.setIsVerified(false);
		userobj.setAction(true);
		userobj.setProfile(null);
		return userobj;
	}

	@Override
	public Map<String, Object> verification(String email, String password) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Optional<UserEntity> userEntity = ragistrationRepository.findOneByEmailIgnoreCase(email);

			if (userEntity.isPresent()) {
				UserEntity user = userEntity.get();
				if (user.getPassword().equalsIgnoreCase(password)) {
					user.setIsVerified(true);
					ragistrationRepository.save(user);
					map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_200);
					map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.USER_VERIFICATION_SUCCESS);
					map.put(ApplicationConstant.RESPONSE_DATA, new ArrayList<>());
				}
			} else {
				map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_400);
				map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.USER_VERIFICATION_FAILURE);
				map.put(ApplicationConstant.RESPONSE_DATA, new ArrayList<>());
			}
		} catch (Exception e) {
	
			logger.error(" Problem occured while verification , Please check logs  " + e.getMessage());
			map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.SOMETING_WENT_WRONG);
		}
		return map;
	}

	@Override
	public Map<String, Object> forgetPassword(String email) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Optional<UserEntity> userEntity = ragistrationRepository.findOneByEmailIgnoreCase(email);

			if (userEntity.isPresent()) {
				System.out.println("heloo iam here===============>>");
				UserEntity user = userEntity.get();
				if (user.getIsVerified().equals(Boolean.TRUE)) {
					map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_200);
					map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.FORGOT_PASSWORD_SUCCESS);
					map.put(ApplicationConstant.RESPONSE_DATA, new ArrayList<>());
				} else {
					map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_400);
					map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.LOGIN_NOT_VERIFIED);
					map.put(ApplicationConstant.RESPONSE_DATA, new ArrayList<>());
				}
			} else {
				map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_400);
				map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.FORGOT_PASSWORD_EMAIL_NOT_EXISTS);
				map.put(ApplicationConstant.RESPONSE_DATA, new ArrayList<>());
			}
		} catch (Exception e) {

			logger.error(" Problem occured while ForgetPassword , Please check logs  " + e.getMessage());
			map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.SOMETING_WENT_WRONG);
		}
		return map;
	}

	@Override
	public Map<String, Object> resetPassword(String email, String password, String oldPassword) {
	
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Optional<UserEntity> userEntity = ragistrationRepository.findOneByEmailIgnoreCase(email);
			if (userEntity.isPresent()) {
				UserEntity user = userEntity.get();
				if (user.getPassword().equalsIgnoreCase(oldPassword)) {
					user.setPassword(passwordEncoder.encode(password));
					ragistrationRepository.save(user);

					map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_200);
					map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.RESET_PASSWORD_SUCCESS);
					map.put(ApplicationConstant.RESPONSE_DATA, new ArrayList<>());
				} else {
					map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_400);
					map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.OLD_PASSWORD_NOT_MATCHED);
					map.put(ApplicationConstant.RESPONSE_DATA, new ArrayList<>());
				}
			} else {
				map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_400);
				map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.USER_NOT_FOUND);
				map.put(ApplicationConstant.RESPONSE_DATA, new ArrayList<>());
			}
		} catch (Exception e) {
		
			logger.error(" Problem occured while resetpassword , Please check logs  " + e.getMessage());
			map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.SOMETING_WENT_WRONG);
		}
		return map;
	}
}