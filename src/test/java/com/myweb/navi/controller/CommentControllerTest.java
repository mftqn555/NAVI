package com.myweb.navi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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
public class CommentControllerTest {

	@Autowired
	private MockMvc mockmvc;

	@Autowired
	private ObjectMapper objectMapper;

	@DisplayName("댓글 쓰기 요청")
	@Test
	@Transactional
	void 댓글쓰기() throws Exception {
		Map<String, String> input = new HashMap<>();
		input.put("bno", "11");
		input.put("user_id", "1");
		input.put("content", "테스트3");
		input.put("nickname", "xdvdffd");

		this.mockmvc
				.perform(post("/comments").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(input)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andDo(print());
	}
	
	@DisplayName("대댓글 쓰기 요청")
	@Test
	@Transactional
	void 대댓글쓰기() throws Exception {
		Map<String, String> input = new HashMap<>();
		input.put("bno", "11");
		input.put("user_id", "1");
		input.put("content", "테스트3");
		input.put("nickname", "xdvdffd");
		input.put("re_cno", "15");

		this.mockmvc
				.perform(post("/comments/reply").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(input)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andDo(print());
	}
	
	@DisplayName("내용 빈칸시 400 오류메세지 반환")
	@Test
	@Transactional
	void 댓글쓰기오류_내용빈칸() throws Exception {
		Map<String, String> input = new HashMap<>();
		input.put("bno", "11");
		input.put("user_id", "1");
		input.put("content", "");
		input.put("nickname", "1bsc");

		this.mockmvc
				.perform(post("/comments").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(input)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andDo(print());
	}
	
	@DisplayName("나머지 요소들 잘못되었을 시 500 메세지 반환")
	@Test
	@Transactional
	void 댓글쓰기오류_아이디빈칸() throws Exception {
		Map<String, String> input = new HashMap<>();
		input.put("bno", "11");
		input.put("user_id", "");
		input.put("content", "123");
		input.put("nickname", "1bsc");

		this.mockmvc
				.perform(post("/comments").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(input)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is5xxServerError()).andDo(print());
	}
	
	@DisplayName("댓글 수정 요청")
	@Test
	@Transactional
	void 댓글수정() throws Exception {
		Map<String, String> input = new HashMap<>();
		input.put("cno", "25");
		input.put("content", "대댓글 수정된댓글");

		this.mockmvc
				.perform(patch("/comments").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(input)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andDo(print());
	}
	
	@DisplayName("내용 빈칸으로 수정 요청시 400 오류메세지 반환")
	@Test
	@Transactional
	void 댓글수정오류_내용빈칸() throws Exception {
		Map<String, String> input = new HashMap<>();
		input.put("cno", "25");
		input.put("content", "");

		this.mockmvc
				.perform(patch("/comments").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(input)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andDo(print());
	}
	
	@DisplayName("수정 요청했는데 댓글 없을시 요청시 400 오류메세지 반환")
	@Test
	@Transactional
	void 댓글수정오류_게시글없음() throws Exception {
		Map<String, String> input = new HashMap<>();
		input.put("cno", "999");
		input.put("content", "abcd123");

		this.mockmvc
				.perform(patch("/comments").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(input)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andDo(print());
	}
	
	@DisplayName("댓글 리스트 조회 요청")
	@Test
	void 댓글리스트조회() throws Exception {
		
		this.mockmvc
				.perform(get("/comments/11?currentPage=1"))
				.andExpect(status().isOk()).andDo(print());
	}
	
	@DisplayName("없는 댓글 리스트 조회 요청시 400 오류메세지 반환")
	@Test
	void 댓글리스트조회_없는게시글() throws Exception {
		
		this.mockmvc
				.perform(get("/comments/999?currentPage=1&postsPerPage=5"))
				.andExpect(status().isNotFound()).andDo(print());
	}
	
	@DisplayName("댓글 삭제")
	@Test
	@Transactional
	void 댓글삭제() throws Exception {
		
		this.mockmvc
				.perform(delete(("/comments/14")))
				.andExpect(status().isNoContent()).andDo(print());
	}
	
	@DisplayName("없는 댓글 삭제요청시 400 오류메세지 반환")
	@Test
	@Transactional
	void 댓글삭제_없는댓글삭제요청() throws Exception {
		
		this.mockmvc
				.perform(delete(("/comments/999")))
				.andExpect(status().isNotFound()).andDo(print());
	}

}
