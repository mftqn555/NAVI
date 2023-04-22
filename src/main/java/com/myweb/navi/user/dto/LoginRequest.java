package com.myweb.navi.user.dto;

import lombok.Getter;

@Getter
public class LoginRequest {
	
	private String email;
	private String password;
	
	public LoginRequest() {
	}
	
	public LoginRequest(String email, String password) {
		this.email = email;
		this.password = password;
	}
	
}
