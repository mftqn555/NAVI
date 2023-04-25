package com.myweb.navi.comment.exception;

import com.myweb.navi.advice.BadRequestException;

public class InvalidCommentException extends BadRequestException {
	
	private static final String MESSAGE = "댓글 내용이 빈 칸입니다";
	
	public InvalidCommentException() {
		super(MESSAGE);
	}

}
