package com.myweb.navi.comment.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;

@Getter
public class CommentUpdateRequest {
	
	private Long cno;
	@NotBlank(message = "내용이 빈 칸입니다")
	private String content;

	public CommentUpdateRequest() {
	}

	public CommentUpdateRequest(Long cno, String content) {
		this.cno = cno;
		this.content = content;
	}
	
}
