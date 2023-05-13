package com.myweb.navi.user.service;

import java.util.Set;

import javax.mail.internet.MimeMessage;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.myweb.navi.advice.BusinessException;
import com.myweb.navi.support.ValidateUser;
import com.myweb.navi.user.dto.DeleteUserRequest;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

	private final UserMapper userMapper;
	private final ValidatorFactory factory;
	private final JavaMailSender javaMailSender;

	public UserService(UserMapper userMapper, ValidatorFactory factory, JavaMailSender javaMailSender) {
		this.userMapper = userMapper;
		this.factory = factory;
		this.javaMailSender = javaMailSender;
	}

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
		if (userResponse == null || !userResponse.getPassword().equals(loginRequest.getPassword())) {
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

	public UserResponse findUserInfoByNickname(String nickname) {
		UserResponse userResponse = userMapper.selectUserInfoByNickname(nickname);
		if (userResponse == null) {
			throw new UserNotFoundException();
		}
		return userResponse;
	}

	// 이메일 보내기
	public void sendPasswordByEmail(String email) {
		String password = userMapper.selectPassword(email);
		if (password == null) {
			throw new UserNotFoundException();
		}
		sendMail(email, password);
	}

	// 정보수정
	// 1. 유효성 검사
	// 2. 유저 정보 존재유무 검사
	public void modifyPasswordByUserInfo(PasswordRequest passwordRequest) {
		validatePasswordRequest(passwordRequest);
		if (userMapper.updatePasswordByUserInfo(passwordRequest) != 1) {
			throw new UserNotFoundException();
		}
	}

	public void modifyNicknameByEmail(NicknameRequest nicknameRequest) {
		if (!findExistNickname(nicknameRequest.getNickname()).isUnique()) {
			throw new DuplicateNicknameException();
		}
		if (userMapper.updateNicknameByEmail(nicknameRequest) != 1) {
			throw new UserNotFoundException();
		}
	}

	// 정보삭제
	// 1. 존재하는 아이디인지 확인 후 삭제
	public void removeUserByEmail(DeleteUserRequest deleteUserRequest) {
		if (userMapper.deleteUserByEmail(deleteUserRequest) != 1) {
			throw new UserNotFoundException();
		}
	}

	// 유효성 검사
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
		if (!findExistEmail(signupRequest.getEmail()).isUnique()) {
			throw new DuplicateEmailException();
		}
		if (!findExistNickname(signupRequest.getNickname()).isUnique()) {
			throw new DuplicateNicknameException();
		}
	}

	private void validatePasswordRequest(PasswordRequest passwordRequest) {
		validateField("password", passwordRequest.getNewPassword(), new InvalidPasswordException());
		validateField("password", passwordRequest.getOldPassword(), new InvalidPasswordException());
	}

	// 중복값 검사
	private boolean isUnique(String value) {
		if (value == null) {
			return true;
		}
		return false;
	}

	// 이메일 전송
	private void sendMail(String email, String password) {

		MimeMessage message = javaMailSender.createMimeMessage();

		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

			messageHelper.setFrom("mftqn555@gmail.com", "NAVI");
			messageHelper.setTo(email);
			messageHelper.setSubject("[NAVI] 가입하신 계정의 정보입니다");

			StringBuilder sb = new StringBuilder();
			sb.append(" <p>회원님의 비밀번호는 다음과 같습니다</p>\n");
			sb.append(" <p><strong>비밀번호: ");
			sb.append(password);
			sb.append(" </strong></p>\n");
			sb.append(" <p>로그인 후 반드시 비밀번호를 변경하시기 바랍니다.</p>\n");
			sb.append(" <br>\n");
			sb.append(" <p>감사합니다.</p>\n");

			String emailContent = sb.toString();
			messageHelper.setText(emailContent, true);

			javaMailSender.send(message);
		} catch (Exception e) {
			log.info("메일 보내는 중 오류 발생 {}", e);
		}

	}

}
