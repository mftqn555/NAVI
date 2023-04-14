package com.myweb.navi.user.exception;

import com.myweb.navi.advice.BadRequestException;

public class InvalidNicknameException extends BadRequestException {
	
	private static final String MESSAGE = "잘못된 닉네임 형식입니다";

	public InvalidNicknameException() {
		super(MESSAGE);
	}
	
}
