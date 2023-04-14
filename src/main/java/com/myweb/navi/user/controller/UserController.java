package com.myweb.navi.user.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myweb.navi.user.dto.IdRequest;
import com.myweb.navi.user.dto.NicknameRequest;
import com.myweb.navi.user.dto.PasswordRequest;
import com.myweb.navi.user.dto.SignupRequest;
import com.myweb.navi.user.dto.UniqueResponse;
import com.myweb.navi.user.dto.UserResponse;
import com.myweb.navi.user.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest signupRequest) {
		userService.addUser(signupRequest);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
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
	
	@PostMapping(value = "/modify/info")
	public ResponseEntity<UserResponse> userDetails(@RequestBody IdRequest idRequest) {
		UserResponse userResponse = userService.findUserInfoById(idRequest.getId());
		return ResponseEntity.ok().body(userResponse);
	}
	
	@PostMapping(value = "/modify/password")
	public ResponseEntity<?> passwordModify(@RequestBody PasswordRequest passwordRequest) {
		userService.modifyPasswordById(passwordRequest);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping(value = "/modify/nickname")
	public ResponseEntity<?> nicknameModify(@RequestBody NicknameRequest nicknameRequest) {
		userService.modifyNicknameById(nicknameRequest);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping(value = "/delete")
	public ResponseEntity<?> userRemove(@RequestBody IdRequest idRequest) {
		userService.removeUserById(idRequest.getId());
		return ResponseEntity.noContent().build();
	}
	
}
