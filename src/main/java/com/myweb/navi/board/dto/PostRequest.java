package com.myweb.navi.board.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PostRequest {
	
	private Long user_id;
	private String title;
	private String content;
	private String nickname;
	private String image_url;
	
	public PostRequest() {
	}
	
	public PostRequest(Long user_id, String title, String content, String nickname, String image_url) {
		this.user_id = user_id;
		this.title = title;
		this.content = content;
		this.nickname = nickname;
		this.image_url = image_url;
	}

}
