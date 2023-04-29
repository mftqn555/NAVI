package com.myweb.navi.user.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserResponse implements Serializable {

	private Long id;
	private String email;
	private String password;
	private String nickname;
	
	public UserResponse() {
	}
	
	public UserResponse(Long id, String email, String password, String nickname) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.nickname = nickname;
	}
	
}
