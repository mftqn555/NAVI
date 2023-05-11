package com.myweb.navi.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PostRequest {
	
	private Long user_id;
	private String title;
	private String content;
	private String nickname;
	private boolean admin;
	
	public PostRequest() {
	}

	@Builder
	public PostRequest(Long user_id, String title, String content, String nickname, boolean admin) {
		this.user_id = user_id;
		this.title = title;
		this.content = content;
		this.nickname = nickname;
		this.admin = admin;
	}

}
