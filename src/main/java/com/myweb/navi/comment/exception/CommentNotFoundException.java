package com.myweb.navi.comment.exception;

import com.myweb.navi.advice.NotFoundException;

public class CommentNotFoundException extends NotFoundException {
	
	private static final String MESSAGE = "댓글 정보를 찾을 수 없습니다";
	
	public CommentNotFoundException() {
		super(MESSAGE);
	}

}
