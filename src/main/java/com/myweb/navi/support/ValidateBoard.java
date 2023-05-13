package com.myweb.navi.support;

import javax.validation.constraints.NotBlank;

public class ValidateBoard {

	@NotBlank(message = "제목이 빈 칸입니다")
	private String title;
	@NotBlank(message = "글 내용이 빈 칸입니다")
	private String content;

}
