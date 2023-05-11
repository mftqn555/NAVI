package com.myweb.navi.connector.dto;

import lombok.Getter;

@Getter
public class DustRequest {

	private String sidoName;
	
	public DustRequest() {
	}

	public DustRequest(String sidoName) {
		this.sidoName = sidoName;
	}
	
}
