package com.myweb.navi.user.dto;

import lombok.Getter;

@Getter
public class NicknameRequest {
	
	private Long id;
	private String nickname;
	
	public NicknameRequest() {
	}
	
	public NicknameRequest(Long id, String nickname) {
		this.id = id;
		this.nickname = nickname;
	}
	
}
