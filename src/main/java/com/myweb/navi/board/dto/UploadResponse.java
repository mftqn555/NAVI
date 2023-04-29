package com.myweb.navi.board.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UploadResponse {
	
	private Integer uploaded;
	private String fileName;
	private String url;
	
	public UploadResponse() {
	}
	
	@Builder
	public UploadResponse(Integer uploaded, String fileName, String url) {
		this.uploaded = uploaded;
		this.fileName = fileName;
		this.url = url;
	}

}
