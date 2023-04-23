package com.myweb.navi.board.dto;

import java.sql.Timestamp;

import lombok.Getter;

@Getter
public class BoardResponse {
	
	private Long bno;
	private String title;
	private String content;
	private int view_count;
	private Timestamp create_date;
	private String nickname;
	private String image_url;
	
	public BoardResponse() {
	}

	public BoardResponse(Long bno, String title, String content, int view_count, Timestamp create_date, String nickname,
			String image_url) {
		this.bno = bno;
		this.title = title;
		this.content = content;
		this.view_count = view_count;
		this.create_date = create_date;
		this.nickname = nickname;
		this.image_url = image_url;
	}

}
