package com.myweb.navi.comment.dto;

import lombok.Getter;

@Getter
public class CommentRequest {

	private Long bno;
	private Long user_id;
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
