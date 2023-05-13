package com.myweb.navi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myweb.navi.user.dto.DeleteUserRequest;
import com.myweb.navi.user.dto.LoginRequest;
import com.myweb.navi.user.dto.NicknameRequest;
import com.myweb.navi.user.dto.PasswordRequest;
import com.myweb.navi.user.dto.SignupRequest;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	private MockMvc mockmvc;

	@Autowired
	private ObjectMapper objectMapper;

	@DisplayName("회원가입 테스트 - 성공")
	@Test
	@Transactional
	void 회원가입테스트_성공() throws Exception {
		SignupRequest signupRequest = SignupRequest.builder().email("test3@google.com").password("test123")
				.nickname("nickname").build();

		this.mockmvc.perform(post("/users/signup").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(signupRequest)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andDo(print());
	}

	@DisplayName("회원가입 테스트 - 이메일 형식 실패")
	@Test
	@Transactional
	void 회원가입테스트_이메일_형식_실패() throws Exception {
		SignupRequest signupRequest = SignupRequest.builder().email("testgoogle.com").password("test123")
				.nickname("nickname").build();

		this.mockmvc.perform(post("/users/signup").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(signupRequest)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@DisplayName("회원가입 테스트 - 닉네임 형식 실패")
	@Test
	@Transactional
	void 회원가입테스트_닉네임_형식_실패() throws Exception {
		SignupRequest signupRequest = SignupRequest.builder().email("testgoogle.com").password("test123").nickname("a")
				.build();

		this.mockmvc.perform(post("/users/signup").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(signupRequest)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@DisplayName("회원가입 테스트 - 비밀번호 형식 실패")
	@Test
	@Transactional
	void 회원가입테스트_비밀번호_형식_실패() throws Exception {
		SignupRequest signupRequest = SignupRequest.builder().email("testgoogle.com").password("t").nickname("aabc1")
				.build();

		this.mockmvc.perform(post("/users/signup").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(signupRequest)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	// 이메일 체크
	@DisplayName("요청한 이메일값이 유니크한 값인가? - false")
	@Test
	void 이메일중복체크_거짓() throws Exception {
		this.mockmvc
				.perform(get("/users/exist").param("email", "abc@naver.com").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.unique", "false").exists()).andDo(print());
	}

	@DisplayName("요청한 이메일값이 유니크한 값인가? - true")
	@Test
	void 이메일중복체크_참() throws Exception {
		this.mockmvc.perform(get("/users/exist").param("email", "bd@naver.com").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.unique", "false").exists()).andDo(print());
	}

	@DisplayName("잘못된 이메일값 체크요청")
	@Test
	void 잘못된_이메일값체크() throws Exception {
		this.mockmvc.perform(get("/users/exist").param("email", "bnaver.com").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	// 닉네임 체크
	@DisplayName("요청한 닉네임값이 유니크한 값인가? - false")
	@Test
	void 닉네임중복체크_거짓() throws Exception {
		this.mockmvc.perform(get("/users/exist").param("nickname", "cdc").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.unique", "false").exists()).andDo(print());
	}

	@DisplayName("요청한 닉네임값이 유니크한 값인가? - true")
	@Test
	void 닉네임중복체크_참() throws Exception {
		this.mockmvc.perform(get("/users/exist").param("nickname", "bcde").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.unique", "false").exists()).andDo(print());
	}

	@DisplayName("잘못된 닉네임값 체크요청")
	@Test
	void 잘못된_닉네임값체크() throws Exception {
		this.mockmvc.perform(get("/users/exist").param("nickname", "er.com").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@DisplayName("이메일로 회원정보 조회")
	@Test
	void 회원정보조회() throws Exception {
		String request = "abc@naver.com";

		this.mockmvc.perform(post("/users/info").contentType(MediaType.APPLICATION_JSON).content(request)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(print());
	}

	@DisplayName("없는_회원정보 조회")
	@Test
	void 없는_회원정보조회() throws Exception {
		String request = "12";

		this.mockmvc.perform(post("/users/modify").contentType(MediaType.APPLICATION_JSON).content(request)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound()).andDo(print());
	}

	@DisplayName("비밀번호 수정")
	@Test
	@Transactional
	void 비밀번호수정() throws Exception {
		PasswordRequest passwordRequest = PasswordRequest.builder().email("abc@naver.com").oldPassword("ab456789")
				.newPassword("ab456798").build();

		this.mockmvc.perform(patch("/users/password").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(passwordRequest)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent()).andDo(print());
	}

	@DisplayName("비밀번호 수정 - 현재 비밀번호 틀림")
	@Test
	@Transactional
	void 현재비밀번호틀림() throws Exception {
		PasswordRequest passwordRequest = PasswordRequest.builder().email("abc@naver.com").oldPassword("ab45677")
				.newPassword("ab456798").build();

		this.mockmvc.perform(patch("/users/password").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(passwordRequest)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andDo(print());
	}

	@DisplayName("비밀번호 수정 - 비밀번호 형식 틀림")
	@Test
	@Transactional
	void 비밀번호형식틀림() throws Exception {
		PasswordRequest passwordRequest = PasswordRequest.builder().email("abc@naver.com").oldPassword("ab456789")
				.newPassword("ab").build();

		this.mockmvc.perform(patch("/users/password").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(passwordRequest)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@DisplayName("비밀번호 수정 - 유저정보없음")
	@Test
	@Transactional
	void 비밀번호_유저정보없음() throws Exception {
		PasswordRequest passwordRequest = PasswordRequest.builder().email("c@naver.com").oldPassword("ab456789")
				.newPassword("ab456798").build();

		this.mockmvc.perform(patch("/users/password").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(passwordRequest)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andDo(print());
	}

	@DisplayName("닉네임 수정")
	@Test
	@Transactional
	void 닉네임수정() throws Exception {
		NicknameRequest nicknameRequest = NicknameRequest.builder().email("abc@naver.com").nickname("aox").build();

		this.mockmvc.perform(patch("/users/nickname").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(nicknameRequest)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent()).andDo(print());
	}

	@DisplayName("닉네임 수정 - 닉네임 형식 틀림")
	@Test
	@Transactional
	void 닉네임형식틀림() throws Exception {
		NicknameRequest nicknameRequest = NicknameRequest.builder().email("abc@naver.com").nickname("a").build();

		this.mockmvc.perform(patch("/users/nickname").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(nicknameRequest)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@DisplayName("닉네임 수정 - 이미 존재하는 닉네임")
	@Test
	@Transactional
	void 닉네임_존재하는닉네임() throws Exception {
		NicknameRequest nicknameRequest = NicknameRequest.builder().email("abc@naver.com").nickname("cdc").build();

		this.mockmvc.perform(patch("/users/nickname").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(nicknameRequest)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@DisplayName("닉네임 수정 - 유저 정보 없음")
	@Test
	@Transactional
	void 닉네임_유저정보없음() throws Exception {
		NicknameRequest nicknameRequest = NicknameRequest.builder().email("cdc@naver.com").nickname("ab12").build();

		this.mockmvc.perform(patch("/users/nickname").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(nicknameRequest)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andDo(print());
	}

	@DisplayName("유저 정보 삭제")
	@Test
	@Transactional
	void 유저정보삭제() throws Exception {
		DeleteUserRequest deleteUserRequest = DeleteUserRequest.builder().email("abc@naver.com").password("ab456789")
				.build();

		this.mockmvc.perform(delete("/users").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(deleteUserRequest)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent()).andDo(print());
	}

	@DisplayName("유저 정보 삭제 - 유저 정보 없음")
	@Test
	@Transactional
	void 삭제_유저정보없음() throws Exception {
		DeleteUserRequest deleteUserRequest = DeleteUserRequest.builder().email("d@naver.com").password("ab456789")
				.build();

		this.mockmvc.perform(delete("/users").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(deleteUserRequest)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andDo(print());
	}

	@DisplayName("로그인")
	@Test
	void 로그인() throws Exception {
		LoginRequest loginRequest = LoginRequest.builder().email("abc@naver.com").password("ab456789").build();

		this.mockmvc
				.perform(post("/users/login").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(loginRequest)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print());
	}

	@DisplayName("로그인실패")
	@Test
	void 로그인실패() throws Exception {
		LoginRequest loginRequest = LoginRequest.builder().email("abc@naver.com").password("abc456").build();

		this.mockmvc
				.perform(post("/users/login").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(loginRequest)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andDo(print());
	}

}
