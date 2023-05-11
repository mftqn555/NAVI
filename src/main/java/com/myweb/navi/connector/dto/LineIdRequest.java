package com.myweb.navi.connector.dto;

import lombok.Getter;

@Getter
public class LineIdRequest {
	
	private String lineId;

	public LineIdRequest() {
	}
	
	public LineIdRequest(String lineId) {
		this.lineId = lineId;
	}
	
}
