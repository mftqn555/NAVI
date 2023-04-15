package com.myweb.navi.user.dto;

import lombok.Getter;

@Getter
public class SignupRequest {
	
	private String email;
	private String password;
	private String nickname;
	
	public SignupRequest() {
	}
	
	public SignupRequest(String email, String password, String nickname) {
		this.email = email;
		this.password = password;
		this.nickname = nickname;
	}

}
