package com.example.overseas.consultancy.service;

import java.util.Map;
import com.example.overseas.consultancy.entity.UserDto;

public interface RagistrationService 
{
	public Map<String, Object> SaveUser(UserDto user,String action);

	public Map<String, Object> verification(String email, String password);

	public Map<String, Object> forgetPassword(String email);

	public Map<String, Object> resetPassword(String email, String password, String oldPassword);

}
