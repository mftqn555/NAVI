package com.myweb.navi.user.service;

import org.springframework.stereotype.Service;

import com.myweb.navi.user.dto.SignupRequest;
import com.myweb.navi.user.dto.UniqueResponse;
import com.myweb.navi.user.dto.UserResponse;
import com.myweb.navi.user.exception.UserNotFoundException;
import com.myweb.navi.user.mapper.UserMapper;

@Service
public class UserService {
	
	// 예외 생성하기
	
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
	
	public UniqueResponse findExistNickname(String nickname) {
		boolean unique = userMapper.selectNickname(nickname) == null ? true : false;
		return new UniqueResponse(unique);
	}

	public UserResponse findUserInfoById(Long id) {
		UserResponse userResponse = userMapper.selectUserInfoById(id);
		if(userResponse == null) throw new UserNotFoundException();
		return userResponse;
	}

}
