package com.example.overseas.consultancy.serviceImpl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.overseas.consultancy.Repository.RagistrationRepository;
import com.example.overseas.consultancy.entity.UserEntity;

import lombok.extern.slf4j.Slf4j;

@Service("customUserService")
@Slf4j
public class UserDetailserviceImpl implements UserDetailsService {

	public static final Logger logger = LoggerFactory.getLogger(UserDetailserviceImpl.class);

	@Autowired
	private RagistrationRepository ragistrationRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		logger.info("inside loadUserByUsername method");

		Optional<UserEntity> entity = ragistrationRepository.findByEmailIgnoreCase(username);
		UserEntity userEntity = null;

		if (entity.isPresent()) {
			userEntity = entity.get();
		}
		if (userEntity == null) {
			throw new UsernameNotFoundException("", new Throwable("Invalid Creds"));
		}
		UserDetails user = User.withUsername(userEntity.getEmail()).password(userEntity.getPassword())
				.authorities("USER").build();
		return user;
	}
}