package com.myweb.navi.board.exception;

import com.myweb.navi.advice.BadRequestException;

public class InvalidContentException extends BadRequestException {
	
	private static final String MESSAGE = "내용이 빈 칸입니다";
	
	public InvalidContentException() {
		super(MESSAGE);
	}
	
}
