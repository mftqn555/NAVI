package com.myweb.navi.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PasswordRequest {
	
	private String email;
	private String oldPassword;
	private String newPassword;
	
	public PasswordRequest() {
	}
	
	@Builder
	public PasswordRequest(String email, String oldPassword, String newPassword) {
		this.email = email;
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}
	
}
