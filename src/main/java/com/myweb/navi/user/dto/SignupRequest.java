package com.myweb.navi.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Getter;

@Getter
public class SignupRequest {
	
	// 중복값은 다른 API 호출하여 체크 후, 체크가 안되면 못넘어가게 프론트에서 설정
	// 비밀번호 - 6자 이상 숫자, 알파벳 포함
	// 닉네임 - 2~8자리 영어 또는 숫자 또는 한글
	
	@NotBlank(message = "이메일이 빈칸입니다")
	@Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "잘못된 이메일 형식입니다")
	private String email;
	@NotBlank(message = "비밀번호가 빈칸입니다")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d\\w\\W]{6,}$", message = "잘못된 비밀번호 형식입니다")
	private String password;
	@NotBlank(message = "닉네임이 빈칸입니다")
	@Pattern(regexp = "^(?=.*[a-z0-9가-힣])[a-z0-9가-힣]{2,8}$", message = "잘못된 닉네임 형식입니다")
	private String nickname;
	
	public SignupRequest() {
	}
	
	public SignupRequest(String email, String password, String nickname) {
		this.email = email;
		this.password = password;
		this.nickname = nickname;
	}

}
