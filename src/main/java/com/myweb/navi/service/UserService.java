package com.myweb.navi.service;

import org.springframework.stereotype.Service;

import com.myweb.navi.dto.SignupRequest;
import com.myweb.navi.dto.UniqueResponse;
import com.myweb.navi.mapper.UserMapper;

@Service
public class UserService {
	
	private final UserMapper userMapper;
	
	public UserService(UserMapper userMapper) {
		this.userMapper = userMapper;
	}
	
	public void addUser(SignupRequest signupRequest) {
		userMapper.insertUser(signupRequest);
	}
	
	public UniqueResponse findExistEmail(String email) {
		boolean unique = userMapper.selectEmail(email) == null ? true : false;
		return new UniqueResponse(unique); 
	}

}
