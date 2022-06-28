package com.example.overseas.consultancy.serviceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.overseas.consultancy.Constant.ApplicationConstant;
import com.example.overseas.consultancy.Repository.RagistrationRepository;
import com.example.overseas.consultancy.entity.UserDto;
import com.example.overseas.consultancy.entity.UserEntity;
import com.example.overseas.consultancy.service.MailService;
import com.example.overseas.consultancy.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	public static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private RagistrationRepository ragistrationRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private MailService mailservice;

	@Override
	public Map<String, Object> uplodProfilePic(Integer id, MultipartFile multipartFile) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserEntity userEntity = ragistrationRepository.getById(id);
			Cloudinary cloudinary = (Cloudinary) new Cloudinary(
					ObjectUtils.asMap("cloud_name", ApplicationConstant.CLOUD_NAME, "api_key",
							ApplicationConstant.API_KEY, "api_secret", ApplicationConstant.API_SECRET));

			Map uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(),
					ObjectUtils.asMap("public_id", "user_profile/" + id));

			String url = uploadResult.get("url").toString();
			userEntity.setProfile(url);
			ragistrationRepository.save(userEntity);

			map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_200);
			map.put(ApplicationConstant.RESPONSE_DATA, url);
			map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.PROFILE_IMAGE_SUCESSFULLY);
		} catch (IOException e) {
			e.printStackTrace();
			map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_400);
			map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.SOMETING_WENT_WRONG);
		}
		return map;
	}

	@Override
	public Map<String, Object> getuserprofile(Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Optional<UserEntity> userEntity = ragistrationRepository.findById(id);

			if (userEntity.isPresent()) {
				map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_200);
				map.put(ApplicationConstant.RESPONSE_DATA, userEntity.get().getProfile());
				map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.PROFILE_IMAGE_SUCESSFULLY);
			} else {
				map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_400);
				map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.SOMETING_WENT_WRONG);
			}
		} catch (Exception e) {
			logger.error("Problem occured while getProfile , Please check logs : " + e.getMessage());
		}
		return map;
	}

	@Override
	public Map<String, Object> getrole(String role) {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<UserEntity> userEntity = ragistrationRepository.findbyrole(role);
			List<UserDto> userList = new ArrayList<UserDto>();
			if (!userEntity.isEmpty()) {
				for (UserEntity userDto : userEntity) {
					UserDto user = new UserDto();
					user.setEmail(userDto.getEmail());
					user.setName(userDto.getName());
					user.setRole(userDto.getRole());
					user.setSpecification(userDto.getSpecification());
					user.setPassword("*******");
					userList.add(user);
				}
				map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_200);
				map.put(ApplicationConstant.RESPONSE_DATA, userList);
				map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.USER_LIST_SUCCESS);
			} else {
				map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_400);
				map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.USER_NOT_FOUND);
			}
		} catch (Exception e) {
			logger.error("Problem occured while getrole , Please check logs : " + e.getMessage());
			map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.SOMETING_WENT_WRONG);
		}
		return map;
	}

	@Override
	public Map<String, Object> editUserProfile(Integer id, String name, String email, String role,
			String specification) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> mapObj = new HashMap<String, Object>();
		try {
			Optional<UserEntity> userEntity = ragistrationRepository.findById(id);
			if (userEntity.isPresent()) {
				UserEntity user = userEntity.get();
				user.setName(name);
				user.setEmail(email);
				user.setRole(role);
				user.setSpecification(specification);
				ragistrationRepository.save(user);

				mapObj.put("name", userEntity.get().getName());
				mapObj.put("email", userEntity.get().getEmail());
				mapObj.put("role", userEntity.get().getRole());
				mapObj.put("specification", userEntity.get().getSpecification());

				map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_200);
				map.put(ApplicationConstant.RESPONSE_DATA, mapObj);
				map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.USER_UPDATE_SUCCESS);
			} else {
				map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_400);
				map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.USER_NOT_FOUND);
			}
		} catch (Exception e) {
			logger.error("Problem occured while editUserProfile ", e);
			map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.SOMETING_WENT_WRONG);
		}
		return map;
	}

	@Override
	public Map<String, Object> deleteuser(Integer Id) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> mapObj = new HashMap<String, Object>();
		try {
			Optional<UserEntity> userEntity = ragistrationRepository.findById(Id);

			if (userEntity.isPresent()) {

				ragistrationRepository.deleteFromUser(userEntity.get().getId());

				mapObj.put("name", userEntity.get().getName());
				mapObj.put("email", userEntity.get().getEmail());
				mapObj.put("role", userEntity.get().getRole());
				mapObj.put("specification", userEntity.get().getSpecification());
				map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_200);
				map.put(ApplicationConstant.RESPONSE_DATA, mapObj);
				map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.USER_DELETE);
			} else {
				map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_400);
				map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.USER_NOT_FOUND);
			}
		} catch (Exception e) {
			logger.error("Problem occured while editUserProfile ", e);
			map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.SOMETING_WENT_WRONG);
		}
		return map;
	}

	@Override
	public Map<String, Object> updateUserName(Integer id, String name) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> mapObj = new HashMap<String, Object>();
		try {
			Optional<UserEntity> userEntity = ragistrationRepository.findById(id);
			if (userEntity.isPresent()) {
				UserEntity user = userEntity.get();
				user.setName(name);
				ragistrationRepository.save(user);

				mapObj.put("name", user.getName());
				mapObj.put("email", user.getEmail());
				mapObj.put("role", user.getRole());
				mapObj.put("specification", user.getSpecification());
				map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_200);
				map.put(ApplicationConstant.RESPONSE_DATA, mapObj);
				map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.USER_NAME_UPDATE_SUCCESS);
			} else {
				map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_400);
				map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.USER_NOT_FOUND);
			}
		} catch (Exception e) {
			logger.error("Problem occured while editUserProfile ", e);
			map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.SOMETING_WENT_WRONG);
		}
		return map;
	}

	@Override
	public Map<String, Object> editUserProfilePicture(Integer id, MultipartFile multipartFile) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Optional<UserEntity> userEntity = ragistrationRepository.findById(id);
			if (userEntity.isPresent()) {
				UserEntity user = userEntity.get();
				if (!user.getProfile().isEmpty()) {
					Cloudinary cloudinary = (Cloudinary) new Cloudinary(
							ObjectUtils.asMap("cloud_name", ApplicationConstant.CLOUD_NAME, "api_key",
									ApplicationConstant.API_KEY, "api_secret", ApplicationConstant.API_SECRET));
					Map uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(),
							ObjectUtils.asMap("public_id", "user_profile/" + id));

					String url = uploadResult.get("url").toString();
					user.setProfile(url);
					map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_200);
					map.put(ApplicationConstant.RESPONSE_DATA, url);
					map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.PROFILE_UPLOADED_SUCESSFULLY);
				} else {
					map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_400);
					map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.SOMETING_WENT_WRONG);
				}
			} else {
				map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_400);
				map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.USER_NOT_FOUND);
			}
		} catch (Exception e) {
			logger.error("Problem occured while editUserProfile ", e);
			map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.SOMETING_WENT_WRONG);
		}
		return map;
	}

	@Override
	public Map<String, Object> setUserInactiveUserOrActive(Integer Id, String action, String role) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> mapObj = new HashMap<String, Object>();
		try {
			if (action.equalsIgnoreCase("deactivate")) {
				Optional<UserEntity> userEntity = ragistrationRepository.findById(Id);

				if (userEntity.isPresent()) {

					if (role.equalsIgnoreCase("admin")) {
						UserEntity user = userEntity.get();
						user.setAction(false);
						ragistrationRepository.save(user);

						mapObj.put("name", user.getName());
						mapObj.put("email", user.getEmail());
						mapObj.put("role", user.getRole());
						mapObj.put("specification", user.getSpecification());
						mapObj.put("action", "deactivate");

						map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_200);
						map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.USER_DEACTIVATE_SUCCESS);
						map.put(ApplicationConstant.RESPONSE_DATA, mapObj);
					} else {
						map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_400);
						map.put(ApplicationConstant.RESPONSE_MESSAGE,
								ApplicationConstant.USER_DEACTIVATE_ONLY_BY_ADMIN);
					}
				} else {
					map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_400);
					map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.USER_NOT_FOUND);
					map.put(ApplicationConstant.RESPONSE_DATA, new ArrayList<>());
				}
			} else if (action.equalsIgnoreCase("activateByAdmin")) {
				Optional<UserEntity> userEntity = ragistrationRepository.findById(Id);
				if (userEntity.isPresent()) {
					if (role.equalsIgnoreCase("admin")) {

						UserEntity user = userEntity.get();
						if (user.getAction().equals(false)) {

							user.setAction(true);
							ragistrationRepository.save(user);

							mapObj.put("name", user.getName());
							mapObj.put("email", user.getEmail());
							mapObj.put("role", user.getRole());
							mapObj.put("specification", user.getSpecification());
							mapObj.put("action", "activate");
							map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_200);
							map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.USER_ACTIVATE_SUCCESS);
							map.put(ApplicationConstant.RESPONSE_DATA, mapObj);

						} else {
							map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_400);
							map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.USER_ALLREDAY_ACTIVE);
						}
					} else {
						map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_400);
						map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.USER_ACTIVATE_ONLY_BY_ADMIN);
					}
				} else {
					map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_400);
					map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.USER_NOT_FOUND);
					map.put(ApplicationConstant.RESPONSE_DATA, new ArrayList<>());
				}
			}
		} catch (Exception e) {
			logger.error(" Problem occured while Deactivate User, Please check logs  " + e.getMessage());
			map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.SOMETING_WENT_WRONG);
		}
		return map;
	}

	@Override
	public Map<String, Object> resatPasswordByOldpassword(String email, String oldpassword, String password) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Optional<UserEntity> userEntity = ragistrationRepository.findByEmailIgnoreCase(email);
			if (userEntity.isPresent()) {
				UserEntity user = userEntity.get();
				BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
				boolean isPasswordMatches = bcrypt.matches(oldpassword, user.getPassword());
				if (isPasswordMatches) {
					user.setPassword(passwordEncoder.encode(password));
					ragistrationRepository.save(user);
					map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_200);
					map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.RESET_PASSWORD_SUCCESS);
					map.put(ApplicationConstant.RESPONSE_DATA, new ArrayList<>());
				} else {
					map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_400);
					map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.OLD_PASSWORD_DOES_NOT_MATCHED);
					map.put(ApplicationConstant.RESPONSE_DATA, new ArrayList<>());
				}
			} else {
				map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_400);
				map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.USER_NOT_FOUND);
				map.put(ApplicationConstant.RESPONSE_DATA, new ArrayList<>());
			}
		} catch (Exception e) {
			logger.error(" Problem occured while resat Password By Oldpassword, Please check logs  " + e.getMessage());
			map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.SOMETING_WENT_WRONG);
		}
		return map;
	}
}
