package com.myweb.navi.connector.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class StationInfoRequest {
	
	private String stationId;
	private String upDownType;
	
	public StationInfoRequest() {
	}

	@Builder
	public StationInfoRequest(String stationId, String upDownType) {
		this.stationId = stationId;
		this.upDownType = upDownType;
	}

}
