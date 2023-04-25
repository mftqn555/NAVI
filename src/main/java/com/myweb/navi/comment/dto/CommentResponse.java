package com.myweb.navi.comment.dto;

import java.sql.Timestamp;

import lombok.Getter;

@Getter
public class CommentResponse {

	private Long cno;
	private String content;
	private Timestamp create_date;
	private String nickname;
	private Long user_id;
	private Long re_cno;
	
	public CommentResponse() {
	}

	public CommentResponse(Long cno, String content, Timestamp create_date, String nickname, Long user_id,
			Long re_cno) {
		this.cno = cno;
		this.content = content;
		this.create_date = create_date;
		this.nickname = nickname;
		this.user_id = user_id;
		this.re_cno = re_cno;
	}
	
}
