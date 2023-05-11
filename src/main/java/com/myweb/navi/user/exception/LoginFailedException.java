package com.myweb.navi.user.exception;

import com.myweb.navi.advice.NotFoundException;

public class LoginFailedException extends NotFoundException{
	
	private static final String MESSAGE = "로그인 해주세요";
	
	public LoginFailedException() {
		super(MESSAGE);
	}
}
