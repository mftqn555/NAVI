package com.myweb.navi.user.service;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.stereotype.Service;

import com.myweb.navi.advice.BusinessException;
import com.myweb.navi.support.ValidateUser;
import com.myweb.navi.user.dto.LoginRequest;
import com.myweb.navi.user.dto.NicknameRequest;
import com.myweb.navi.user.dto.PasswordRequest;
import com.myweb.navi.user.dto.SignupRequest;
import com.myweb.navi.user.dto.UniqueResponse;
import com.myweb.navi.user.dto.UserResponse;
import com.myweb.navi.user.exception.DuplicateEmailException;
import com.myweb.navi.user.exception.DuplicateNicknameException;
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

	/**
	 * API
	 */

	// 회원가입
	// 1. 유효성검사
	// 2. 이메일 중복검사(유효성 검사 포함)
	// 3. 닉네임 중복검사(유효성 검사 포함)
	public void addUser(SignupRequest signupRequest) {
		validateSignupRequest(signupRequest);
		userMapper.insertUser(signupRequest);
	}
	
	public UserResponse loginUser(LoginRequest loginRequest) {
		UserResponse userResponse = userMapper.selectUserInfoByEmail(loginRequest.getEmail());
		if(!userResponse.getPassword().equals(userResponse.getPassword())) {
			throw new UserNotFoundException();
		}
		return userResponse;
	}

	// 중복체크
	// 1. 유효성검사
	// 2. 값 가져오기
	// 3. 중복되는 값인지 검사
	public UniqueResponse findExistEmail(String inputEmail) {
		validateField("email", inputEmail, new InvalidEmailException());
		String email = userMapper.selectEmail(inputEmail);
		return new UniqueResponse(isUnique(email));
	}

	public UniqueResponse findExistNickname(String inputNickname) {
		validateField("nickname", inputNickname, new InvalidNicknameException());
		String nickname = userMapper.selectNickname(inputNickname);
		return new UniqueResponse(isUnique(nickname));
	}

	// 정보조회
	// 1. 유저정보 존재유무 검사 후 정보 반환
	public UserResponse findUserInfoByEmail(String email) {
		UserResponse userResponse = userMapper.selectUserInfoByEmail(email);
		if (userResponse == null) {
			throw new UserNotFoundException();
		}
		return userResponse;
	}

	// 정보수정
	// 1. 유효성 검사
	// 2. 유저 정보 존재유무 검사
	public void modifyPasswordByEmail(PasswordRequest passwordRequest) {
		validateField("password", passwordRequest.getPassword(), new InvalidPasswordException());
		isUserNull(passwordRequest.getEmail());
		userMapper.updatePasswordByEmail(passwordRequest);
	}
	
	public void modifyNicknameByEmail(NicknameRequest nicknameRequest) {
		if(!findExistEmail(nicknameRequest.getNickname()).isUnique()) {
			throw new DuplicateEmailException();
		}
		isUserNull(nicknameRequest.getEmail());
		userMapper.updateNicknameByEmail(nicknameRequest);
	}

	// 정보삭제
	// 1. 존재하는 아이디인지 확인 후 삭제
	public void removeUserByEmail(String email) {
		if (userMapper.selectUserInfoByEmail(email) == null) {
			throw new UserNotFoundException();
		}
		userMapper.deleteUserByEmail(email);
	}

	/**
	 * 유효성 검사용 메서드
	 */
	
	private Validator setUpValidator() {
		return factory.getValidator();
	}

	// ValidateUser 클래스를 따로 만들어 서비스레이어에서 유효성 검사
	private void validateField(String fieldName, String fieldValue, BusinessException exception) {
		Validator validator = setUpValidator();
		Set<ConstraintViolation<ValidateUser>> constraintViolations = validator.validateValue(ValidateUser.class,
				fieldName, fieldValue);
		if (!constraintViolations.isEmpty()) {
			throw exception;
		}
	}
	
	private void validateSignupRequest(SignupRequest signupRequest) {
		validateField("password", signupRequest.getPassword(), new InvalidPasswordException());
		if(!findExistEmail(signupRequest.getEmail()).isUnique()) {
			throw new DuplicateEmailException();
		}
		if(!findExistNickname(signupRequest.getNickname()).isUnique()) {
			throw new DuplicateNicknameException();
		}
	}
	
	// 유저 정보 존재유무 검사
	private void isUserNull(String email) {
		UserResponse userResponse = userMapper.selectUserInfoByEmail(email);
		if (userResponse == null) {
			throw new UserNotFoundException();
		}
	}

	// 중복값 검사
	private boolean isUnique(String value) {
		if(value == null) {
			return true;
		}
		return false;
	}

}
