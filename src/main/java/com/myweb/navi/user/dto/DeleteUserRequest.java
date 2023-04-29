package com.myweb.navi.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DeleteUserRequest {

	private String email;
	private String password;
	
	public DeleteUserRequest() {
	}
	
	@Builder
	public DeleteUserRequest(String email, String password) {
		this.email = email;
		this.password = password;
	}
	
}
