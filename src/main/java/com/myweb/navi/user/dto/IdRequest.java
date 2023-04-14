package com.myweb.navi.user.dto;

import lombok.Getter;

@Getter
public class IdRequest {
	
	private Long id;
	
	public IdRequest() {
	}

	public IdRequest(Long id) {
		this.id = id;
	}

}
