package com.myweb.navi.user.exception;

import com.myweb.navi.advice.NotFoundException;

public class UserNotFoundException extends NotFoundException{
	
	private static final String MESSAGE = "유저 정보를 찾을 수 없습니다";
	
	public UserNotFoundException() {
		super(MESSAGE);
	}
}
