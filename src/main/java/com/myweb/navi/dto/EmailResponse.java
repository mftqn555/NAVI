package com.myweb.navi.dto;

import lombok.Getter;

@Getter
public class EmailResponse {
	
	private String email;

	public EmailResponse() {
	}
	
	public EmailResponse(String email) {
		this.email = email;
	}
	
}
