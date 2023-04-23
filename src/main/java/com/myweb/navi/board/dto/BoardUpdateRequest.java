package com.myweb.navi.board.dto;

import lombok.Getter;

@Getter
public class BoardUpdateRequest {
	
	private Long bno;
	private String title;
	private String content;
	private String nickname;
	private String image_url;
	
	public BoardUpdateRequest() {
	}
	
	public BoardUpdateRequest(Long bno, String title, String content, String nickname,
			String image_url) {
		this.bno = bno;
		this.title = title;
		this.content = content;
		this.nickname = nickname;
		this.image_url = image_url;
	}

}
