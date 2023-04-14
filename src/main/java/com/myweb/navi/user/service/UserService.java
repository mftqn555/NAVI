package com.myweb.navi.user.service;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.stereotype.Service;

import com.myweb.navi.user.dto.NicknameRequest;
import com.myweb.navi.user.dto.PasswordRequest;
import com.myweb.navi.user.dto.SignupRequest;
import com.myweb.navi.user.dto.UniqueResponse;
import com.myweb.navi.user.dto.UserResponse;
import com.myweb.navi.user.exception.InvalidEmailException;
import com.myweb.navi.user.exception.InvalidNicknameException;
import com.myweb.navi.user.exception.InvalidPasswordException;
import com.myweb.navi.user.exception.UserNotFoundException;
import com.myweb.navi.user.mapper.UserMapper;

@Service
public class UserService {
	
	private final UserMapper userMapper;
	private final ValidatorFactory factory;
	
	public UserService(UserMapper userMapper, ValidatorFactory factory) {
		this.userMapper = userMapper;
		this.factory = factory;
	}
	
	public void addUser(SignupRequest signupRequest) {
		validateEmail(signupRequest.getEmail());
		validateNickname(signupRequest.getNickname());
		validatePassword(signupRequest.getPassword());
		userMapper.insertUser(signupRequest);
	}
	
	public UniqueResponse findExistEmail(String email) {
		validateEmail(email);
		boolean unique = userMapper.selectEmail(email) == null ? true : false;
		return new UniqueResponse(unique); 
	}
	
	public UniqueResponse findExistNickname(String nickname) {
		validateNickname(nickname);
		boolean unique = userMapper.selectNickname(nickname) == null ? true : false;
		return new UniqueResponse(unique);
	}

	public UserResponse findUserInfoById(Long id) {
		UserResponse userResponse = userMapper.selectUserInfoById(id);
		if(userResponse == null) { 
			throw new UserNotFoundException(); 
		}
		return userResponse;
	}
	
	public void modifyPasswordById(PasswordRequest passwordRequest) {
		validatePassword(passwordRequest.getPassword());
		userMapper.updatePasswordById(passwordRequest);
	}
	
	public void modifyNicknameById(NicknameRequest nicknameRequest) {
		validateNickname(nicknameRequest.getNickname());
		userMapper.updateNicknameById(nicknameRequest);
	}
	
	public void removeUserById(Long id) {
		if(userMapper.selectUserInfoById(id) == null) {
			throw new UserNotFoundException();
		}
		userMapper.deleteUserById(id);
	}
	
	// 검증용 메서드
	private Validator setUpValidator() {
        return factory.getValidator();
    }

	private void validateEmail(String email) {
		Validator validator = setUpValidator();
		Set<ConstraintViolation<SignupRequest>> constraintViolations = 
				validator.validateValue(SignupRequest.class, "email", email);
		if(!constraintViolations.isEmpty()) {
			throw new InvalidEmailException();
		}
	}
	
	private void validateNickname(String nickname) {
		Validator validator = setUpValidator();
		Set<ConstraintViolation<SignupRequest>> constraintViolations = 
				validator.validateValue(SignupRequest.class, "nickname", nickname);
		if(!constraintViolations.isEmpty()) {
			throw new InvalidNicknameException();
		}
	}
	
	private void validatePassword(String password) {
		Validator validator = setUpValidator();
		Set<ConstraintViolation<SignupRequest>> constraintViolations = 
				validator.validateValue(SignupRequest.class, "password", password);
		if(!constraintViolations.isEmpty()) {
			throw new InvalidPasswordException();
		}
	}

}
