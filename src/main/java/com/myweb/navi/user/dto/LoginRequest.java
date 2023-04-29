package com.myweb.navi.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginRequest {
	
	private String email;
	private String password;
	
	public LoginRequest() {
	}
	
	@Builder
	public LoginRequest(String email, String password) {
		this.email = email;
		this.password = password;
	}
	
}
