package com.myweb.navi.board.exception;

import com.myweb.navi.advice.BadRequestException;

public class InvalidTitleException extends BadRequestException {
	
	private static final String MESSAGE = "제목이 빈 칸입니다";
	
	public InvalidTitleException() {
		super(MESSAGE);
	}
	
}
