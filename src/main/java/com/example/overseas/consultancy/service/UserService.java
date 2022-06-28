package com.example.overseas.consultancy.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface UserService {

	public Map<String, Object> uplodProfilePic(Integer id, MultipartFile multipartFile);

	public Map<String, Object> getuserprofile(Integer id);

	public Map<String, Object> getrole(String role);

	public Map<String, Object> editUserProfile(Integer id, String name, String email, String role, String specification);

	public Map<String, Object> deleteuser(Integer Id);

	public Map<String, Object> updateUserName(Integer id, String name);

	public Map<String, Object> editUserProfilePicture(Integer id, MultipartFile multipartFile);

	public Map<String, Object> setUserInactiveUserOrActive(Integer Id,String action,String role);

	public Map<String, Object> resatPasswordByOldpassword(String email, String oldpassword, String password);

}
