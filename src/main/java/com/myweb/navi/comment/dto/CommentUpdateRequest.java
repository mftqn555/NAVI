package com.myweb.navi.comment.dto;

import lombok.Getter;

@Getter
public class CommentUpdateRequest {
	
	private Long cno;
	private String content;

	public CommentUpdateRequest() {
	}

	public CommentUpdateRequest(Long cno, String content) {
		this.cno = cno;
		this.content = content;
	}
	
}
