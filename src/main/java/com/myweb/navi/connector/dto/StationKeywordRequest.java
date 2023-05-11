package com.myweb.navi.connector.dto;

import lombok.Getter;

@Getter
public class StationKeywordRequest {
	
	private String keyword;
	
	public StationKeywordRequest() {
	}

	public StationKeywordRequest(String keyword) {
		this.keyword = keyword;
	}

}
