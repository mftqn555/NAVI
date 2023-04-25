package com.myweb.navi.support;

import javax.validation.constraints.NotBlank;

public class ValidateComment {
	
	@NotBlank(message = "댓글 내용이 빈 칸입니다")
	private String content;

}
