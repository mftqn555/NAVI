package com.myweb.navi.board.dto;

import java.sql.Timestamp;

import lombok.Getter;

@Getter
public class BoardResponse {
	
	private Long bno;
	private String title;
	private int view_count;
	private String create_date;
	private String nickname;
	
	public BoardResponse() {
	}

	public BoardResponse(Long bno, String title, int view_count, String create_date, String nickname) {
		this.bno = bno;
		this.title = title;
		this.view_count = view_count;
		this.create_date = create_date;
		this.nickname = nickname;
	}

}
