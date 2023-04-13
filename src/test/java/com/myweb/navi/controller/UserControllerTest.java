package com.myweb.navi.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.myweb.navi.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myweb.navi.dto.SignupRequest;
import com.myweb.navi.dto.UniqueResponse;
import com.myweb.navi.mapper.UserMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private WebApplicationContext wac;

	@MockBean
	UserService userService;
	
	@MockBean
	UserMapper userMapper;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
				.addFilters(new CharacterEncodingFilter("UTF-8", true))
				.alwaysDo(print()).build();
	}
	
	@DisplayName("회원가입 성공시 201 반환")
	@Test
	void 회원가입_성공() throws Exception {
		SignupRequest signupRequest = new SignupRequest("abc123@naver.com", "abc123", "dfadfd");
		mockMvc.perform(post("/users/signup")
				.contentType(MediaType.APPLICATION_JSON)
				.content(switchJsonString(signupRequest)))
				.andExpect(status().isCreated())
				.andDo(print());
	}
	
	@DisplayName("회원가입 실패, 잘못된 데이터 형식")
	@Test
	void 회원가입_실패() throws Exception {
		SignupRequest signupRequest = new SignupRequest("abnavercom", "2", "a");
		mockMvc.perform(post("/users/signup")
				.contentType(MediaType.APPLICATION_JSON)
				.content(switchJsonString(signupRequest)))
				.andExpect(status().isBadRequest())
				.andDo(print());
	}
	
	@DisplayName("이메일 중복 체크")
	@Test
	void 이메일중복체크() throws Exception {
		boolean unique = userMapper.selectEmail("abc@naver.com") == null ? true : false;
		UniqueResponse uniqueResponse = new UniqueResponse(unique);
		
		when(userService.findExistEmail("abc@naver.com")).thenReturn(uniqueResponse);
		mockMvc.perform(MockMvcRequestBuilders
		        .get("/users/signup/exist").param("email", "abc@naver.com"))
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$.unique").value("true"))
		        .andDo(print());
	}
	
	private static String switchJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
