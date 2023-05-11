package com.myweb.navi.connector.dto;

import lombok.Getter;

@Getter
public class BusNumRequest {
	
	private String busNum;

	public BusNumRequest() {
	}
	
	public BusNumRequest(String busNum) {
		this.busNum = busNum;
	}
	
}
