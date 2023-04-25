package com.myweb.navi.comment.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;

@Getter
public class CommentRequest {

	private Long bno;
	private Long user_id;
	@NotBlank(message = "내용이 빈 칸입니다")
	private String content;
	private String nickname;
	private Long re_cno;
	
	public CommentRequest() {
	}

	public CommentRequest(Long bno, Long user_id, String content, String nickname, Long re_cno) {
		this.bno = bno;
		this.user_id = user_id;
		this.content = content;
		this.nickname = nickname;
		this.re_cno = re_cno;
	}
	
}
