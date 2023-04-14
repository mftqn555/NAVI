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
	
	@PostMapping(value = "/modify")
	public ResponseEntity<UserResponse> userDetails(@RequestBody Long id) {
		UserResponse userResponse = userService.findUserInfoById(id);
		return ResponseEntity.ok().body(userResponse);
	}
	
}
