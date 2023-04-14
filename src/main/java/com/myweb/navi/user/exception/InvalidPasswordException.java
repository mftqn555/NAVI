package com.myweb.navi.user.exception;

import com.myweb.navi.advice.BadRequestException;

public class InvalidPasswordException extends BadRequestException {
	
	private static final String MESSAGE = "잘못된 비밀번호 형식입니다";
	
	public InvalidPasswordException() {
		super(MESSAGE);
	}

}
