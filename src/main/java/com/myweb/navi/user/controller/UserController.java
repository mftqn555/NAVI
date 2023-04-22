package com.myweb.navi.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myweb.navi.support.SessionManager;
import com.myweb.navi.user.dto.LoginRequest;
import com.myweb.navi.user.dto.NicknameRequest;
import com.myweb.navi.user.dto.PasswordRequest;
import com.myweb.navi.user.dto.SignupRequest;
import com.myweb.navi.user.dto.UniqueResponse;
import com.myweb.navi.user.dto.UserResponse;
import com.myweb.navi.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
	
	private final UserService userService;
	private final SessionManager sessionManager;
	
	public UserController(UserService userService, SessionManager sessionManager) {
		this.userService = userService;
		this.sessionManager = sessionManager;
	}
	
	// 회원가입
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
		userService.addUser(signupRequest);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	// 이메일, 닉네임 중복 확인
	@GetMapping(value = "/signup/exist", params = "email")
	public ResponseEntity<UniqueResponse> emailCheck(@RequestParam String email) {
		 UniqueResponse uniqueResponse = userService.findExistEmail(email);
		 return ResponseEntity.ok().body(uniqueResponse);
	}
	
	@GetMapping(value = "/signup/exist", params = "nickname")
	public ResponseEntity<UniqueResponse> nicknameCheck(@RequestParam String nickname) {
		UniqueResponse uniqueResponse = userService.findExistNickname(nickname);
		return ResponseEntity.ok().body(uniqueResponse);
	}
	
	// 유저 정보 조회
	@PostMapping(value = "/modify/info")
	public ResponseEntity<UserResponse> userDetails(@RequestBody String email) {
		UserResponse userResponse = userService.findUserInfoByEmail(email);
		return ResponseEntity.ok().body(userResponse);
	}
	
	// 닉네임, 비밀번호 수정
	@PostMapping(value = "/modify/password")
	public ResponseEntity<?> passwordModify(@RequestBody PasswordRequest passwordRequest) {
		userService.modifyPasswordByEmail(passwordRequest);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping(value = "/modify/nickname")
	public ResponseEntity<?> nicknameModify(@RequestBody NicknameRequest nicknameRequest) {
		userService.modifyNicknameByEmail(nicknameRequest);
		return ResponseEntity.noContent().build();
	}
	
	// 회원 탈퇴
	@PostMapping(value = "/delete")
	public ResponseEntity<?> userRemove(@RequestBody String email) {
		userService.removeUserByEmail(email);
		return ResponseEntity.noContent().build();
	}
	
	// 로그인, 브라우저 종료시 로그아웃
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpSession session, HttpServletResponse response) {
		// 아이디 비밀번호 일치하는지 검사 일치하지않을시 예외발생후 예외메세지 전송
		userService.loginUser(loginRequest); 
	    sessionManager.createSession(loginRequest.getEmail(), response, session);
		return ResponseEntity.ok().build();
	}
	
	// 로그아웃
	@GetMapping("/logout")
	public ResponseEntity<?> logout(HttpServletRequest request, HttpSession session) {
		sessionManager.expire(request, session);
		return ResponseEntity.ok().build();
	}
	
	// 테스트 링크
	@GetMapping("/login/test")
	public ResponseEntity<?> loginTest(HttpServletRequest request, HttpSession session) {
		String email = String.valueOf(sessionManager.getSession(request, session));
		log.info("쿠키, 세션으로부터 받아온 이메일" + email);
        return ResponseEntity.ok().build();
	}

}
