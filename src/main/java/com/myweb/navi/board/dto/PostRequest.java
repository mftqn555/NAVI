package com.myweb.navi.board.dto;

import lombok.Getter;

@Getter
public class PostRequest {
	
	private Long id;
	private String title;
	private String content;
	private String nickname;
	private String image_url;
	
	public PostRequest() {
	}
	
	public PostRequest(Long id, String title, String content, String nickname,
			String image_url) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.nickname = nickname;
		this.image_url = image_url;
	}

}
