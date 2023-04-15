package com.myweb.navi.user.exception;

import com.myweb.navi.advice.BadRequestException;

public class DuplicateNicknameException extends BadRequestException {
	
	private static final String MESSAGE = "이미 존재하는 닉네임입니다";
	
	public DuplicateNicknameException() {
		super(MESSAGE);
	}

}
