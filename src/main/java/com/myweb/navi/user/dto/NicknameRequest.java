package com.myweb.navi.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NicknameRequest {
	
	private String email;
	private String nickname;
	
	public NicknameRequest() {
	}
	
	@Builder
	public NicknameRequest(String email, String nickname) {
		this.email = email;
		this.nickname = nickname;
	}
	
}
