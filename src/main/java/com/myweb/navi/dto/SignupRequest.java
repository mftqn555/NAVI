package com.myweb.navi.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import lombok.Getter;

@Getter
public class SignupRequest {
	
	@Email(message = "잘못된 이메일 형식")
	private String email;
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d\\w\\W]{6,}$", message = "6자 이상 숫자, 알파벳 포함")
	private String password;
	@Pattern(regexp = "^(?=.*[a-z0-9가-힣])[a-z0-9가-힣]{2,8}$", message = "2~8자리 영어 또는 숫자 또는 한글")
	private String nickname;
	
	public SignupRequest() {
	}
	
	public SignupRequest(String email, String password, String nickname) {
		this.email = email;
		this.password = password;
		this.nickname = nickname;
	}

}
