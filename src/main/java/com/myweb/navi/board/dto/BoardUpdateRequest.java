package com.myweb.navi.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardUpdateRequest {
	
	private Long bno;
	private String title;
	private String content;
	
	public BoardUpdateRequest() {
	}
	
	@Builder
	public BoardUpdateRequest(Long bno, String title, String content) {
		this.bno = bno;
		this.title = title;
		this.content = content;
	}
}
