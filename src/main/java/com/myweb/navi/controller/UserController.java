package com.myweb.navi.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myweb.navi.dto.SignupRequest;
import com.myweb.navi.dto.UniqueResponse;
import com.myweb.navi.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest signupRequest, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return new ResponseEntity<>(errorResponse(bindingResult), HttpStatus.BAD_REQUEST);
		}
		userService.addUser(signupRequest);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping(value = "/signup/exist", params = "email")
	public ResponseEntity<UniqueResponse> emailCheck(@RequestParam String email) {
		 UniqueResponse uniqueResponse = userService.findExistEmail(email);
		 return ResponseEntity.ok().body(uniqueResponse);
	}
	
	private static Map<String, String> errorResponse(BindingResult bindingResult) {
		Map<String, String> errorMap = new HashMap<>();
		for (FieldError error : bindingResult.getFieldErrors()) {
			errorMap.put("message", error.getDefaultMessage());
		}
		return errorMap;
	}
	
}
