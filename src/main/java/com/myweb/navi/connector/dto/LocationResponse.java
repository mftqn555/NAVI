package com.myweb.navi.connector.dto;

import lombok.Getter;

@Getter
public class LocationResponse {

	private String adress1;

	public LocationResponse() {
	}

	public LocationResponse(String adress1) {
		this.adress1 = adress1;
	}
	
}
