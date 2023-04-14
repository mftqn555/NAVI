package com.myweb.navi.user.exception;

import com.myweb.navi.advice.BadRequestException;

public class InvalidEmailException extends BadRequestException {
	
	private static final String MESSAGE = "잘못된 이메일 형식입니다";
	
	public InvalidEmailException() {
		super(MESSAGE);
	}

}
