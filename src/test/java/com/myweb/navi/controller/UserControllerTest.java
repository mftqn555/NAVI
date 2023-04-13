package com.myweb.navi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
public class UserControllerTest {

	@Autowired
	private MockMvc mockmvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	// 이메일 체크
	@Test
	@DisplayName("요청한 이메일값이 유니크한 값인가? - false")
	void 이메일중복체크_거짓() throws Exception {
		this.mockmvc
				.perform(get("/users/signup/exist").param("email", "abc@naver.com")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.unique", "false").exists())
				.andDo(print());
	}
	
	@Test
	@DisplayName("요청한 이메일값이 유니크한 값인가? - true")
	void 이메일중복체크_참() throws Exception {
		this.mockmvc
				.perform(get("/users/signup/exist").param("email", "bcd@naver.com")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.unique", "false").exists())
				.andDo(print());
	}
	
	// 닉네임 체크
	@Test
	@DisplayName("요청한 닉네임값이 유니크한 값인가? - false")
	void 닉네임중복체크_거짓() throws Exception {
		this.mockmvc
				.perform(get("/users/signup/exist").param("nickname", "abcd")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.unique", "false").exists())
				.andDo(print());
	}
	
	@Test
	@DisplayName("요청한 이메일값이 유니크한 값인가? - true")
	void 닉네임중복체크_참() throws Exception {
		this.mockmvc
				.perform(get("/users/signup/exist").param("nickname", "bcde")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.unique", "false").exists())
				.andDo(print());
	}
	
	// 회원가입 테스트
	@Test
	@DisplayName("회원가입 테스트 - 성공")
	@Transactional // 테스트 수행 후 rollback
	void 회원가입테스트_성공() throws Exception {
		Map<String, String> input = new HashMap<>();
	    input.put("email", "test3@google.com");
	    input.put("password", "teword123");
	    input.put("nickname", "test3");
		
		this.mockmvc
				.perform(post("/users/signup").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(input))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andDo(print());
	}
	
	@Test
	@DisplayName("회원가입 테스트 - 이메일 형식 실패")
	void 회원가입테스트_이메일_형식_실패() throws Exception {
		Map<String, String> input = new HashMap<>();
	    input.put("email", "test2google.com");
	    input.put("password", "teword123");
	    input.put("nickname", "test2");
		
		this.mockmvc
				.perform(post("/users/signup").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(input))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andDo(print());
	}
	
}
