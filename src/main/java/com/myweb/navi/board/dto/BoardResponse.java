package com.myweb.navi.board.dto;

import lombok.Getter;

@Getter
public class BoardResponse {
	
	private Long bno;
	private Long user_id;
	private String title;
	private int view_count;
	private String content;
	private String create_date;
	private String nickname;
	
	public BoardResponse() {
	}

	public BoardResponse(Long bno, Long user_id, String title, int view_count, String content, String create_date,
			String nickname) {
		this.bno = bno;
		this.user_id = user_id;
		this.title = title;
		this.view_count = view_count;
		this.content = content;
		this.create_date = create_date;
		this.nickname = nickname;
	}

}
