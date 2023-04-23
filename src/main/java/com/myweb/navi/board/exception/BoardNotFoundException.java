package com.myweb.navi.board.exception;

import com.myweb.navi.advice.NotFoundException;

public class BoardNotFoundException extends NotFoundException {
	
	private static final String MESSAGE = "게시글을 찾을 수 없습니다";
	
	public BoardNotFoundException() {
		super(MESSAGE);
	}
	
}
