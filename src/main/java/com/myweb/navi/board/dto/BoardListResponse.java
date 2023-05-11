package com.myweb.navi.board.dto;

import lombok.Getter;

@Getter
public class BoardListResponse {
	
	private Long bno;
	private String title;
	private int view_count;
	private String create_date;
	private String nickname;
	
	public BoardListResponse() {
	}

	public BoardListResponse(Long bno, String title, int view_count, String create_date, String nickname) {
		this.bno = bno;
		this.title = title;
		this.view_count = view_count;
		this.create_date = create_date;
		this.nickname = nickname;
	}

}
