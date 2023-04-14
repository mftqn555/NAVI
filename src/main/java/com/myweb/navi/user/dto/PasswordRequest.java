package com.myweb.navi.user.dto;

import lombok.Getter;

@Getter
public class PasswordRequest {
	
	private Long id;
	private String password;
	
	public PasswordRequest() {
	}

	public PasswordRequest(Long id, String password) {
		this.id = id;
		this.password = password;
	}
	
}
