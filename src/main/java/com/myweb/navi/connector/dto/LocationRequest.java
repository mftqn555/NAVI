package com.myweb.navi.connector.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LocationRequest {
	
	private String nx;
	private String ny;
	
	public LocationRequest() {
	}
	
	@Builder
	public LocationRequest(String nx, String ny) {
		this.nx = nx;
		this.ny = ny;
	}
	
}
