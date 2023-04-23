package com.myweb.navi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class BoardControllerTest {

	@Autowired
	private MockMvc mockmvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@Transactional
	@DisplayName("글 쓰기 요청")
	void 글쓰기() throws Exception {
		Map<String, String> input = new HashMap<>();
		input.put("id", "1");
		input.put("title", "테스트 제목");
		input.put("content", "테스트 내용");
		input.put("nickname", "1bsc");
		input.put("image_url", "test_url");

		this.mockmvc
				.perform(post("/boards/post").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(input)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andDo(print());
	}

	@Test
	@Transactional
	@DisplayName("글 수정 요청")
	void 글수정() throws Exception {
		Map<String, String> input = new HashMap<>();
		input.put("bno", "1");
		input.put("title", "수정된 제목");
		input.put("content", "수정된 테스트 내용");
		input.put("nickname", "1bsc");
		input.put("image_url", "test_url");

		this.mockmvc
				.perform(post("/boards/modify").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(input)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent()).andDo(print());
	}

	@Test
	@DisplayName("글 조회하기")
	void 글조회() throws Exception {
		this.mockmvc.perform(get("/boards/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(print());
	}
	
	@Test
	@Transactional
	@DisplayName("글 삭제 요청")
	void 글삭제() throws Exception {
		this.mockmvc
			.perform(post("/boards/delete/1")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent())
			.andDo(print());
	}

}
