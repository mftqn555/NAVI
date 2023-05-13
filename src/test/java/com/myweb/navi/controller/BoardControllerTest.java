package com.myweb.navi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
import com.myweb.navi.board.dto.BoardUpdateRequest;
import com.myweb.navi.board.dto.PostRequest;

@SpringBootTest
@AutoConfigureMockMvc
public class BoardControllerTest {

	@Autowired
	private MockMvc mockmvc;

	@Autowired
	private ObjectMapper objectMapper;

	@DisplayName("글 쓰기 요청")
	@Test
	@Transactional
	void 글쓰기() throws Exception {
		PostRequest postRequest = PostRequest.builder().user_id((long) 1).title("테스트 제목").content("테스트 내용")
				.nickname("xdvdffd").build();

		this.mockmvc
				.perform(post("/boards").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(postRequest)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andDo(print());
	}

	@DisplayName("글 쓰기 요청시 제목이 빈칸일시 오류메시지 반환")
	@Test
	@Transactional
	void 글쓰기_오류_제목() throws Exception {
		PostRequest postRequest = PostRequest.builder().user_id((long) 1).title("").content("테스트 내용").nickname("1bsc")
				.build();

		this.mockmvc
				.perform(post("/boards").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(postRequest)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@DisplayName("글 쓰기 요청시 내용이 빈칸일시 오류메시지 반환")
	@Test
	@Transactional
	void 글쓰기_오류_내용() throws Exception {
		PostRequest postRequest = PostRequest.builder().user_id((long) 1).title("안녕하세요").content("").nickname("1bsc")
				.build();

		this.mockmvc
				.perform(post("/boards").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(postRequest)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@DisplayName("글 수정 요청")
	@Test
	@Transactional
	void 글수정() throws Exception {
		BoardUpdateRequest boardUpdateRequest = BoardUpdateRequest.builder().title("글 제목").content("글 수정 요청 테스트")
				.build();

		this.mockmvc.perform(patch("/boards/11").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(boardUpdateRequest)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent()).andDo(print());
	}

	@DisplayName("존재하지 않는 글 수정 요청시 오류메세지 반환")
	@Test
	@Transactional
	void 글수정_없는글() throws Exception {
		BoardUpdateRequest boardUpdateRequest = BoardUpdateRequest.builder().title("글 제목").content("글 수정 요청 테스트")
				.build();

		this.mockmvc.perform(patch("/boards/999").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(boardUpdateRequest)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andDo(print());
	}

	@DisplayName("글번호로 글 정보 조회")
	@Test
	void 글조회() throws Exception {
		this.mockmvc.perform(get("/boards/11").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(print());
	}

	@DisplayName("없는 글번호로 글 정보 조회할때 오류메세지 반환")
	@Test
	void 글조회_없는글() throws Exception {
		this.mockmvc.perform(get("/boards/3").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
				.andDo(print());
	}

	@DisplayName("페이징 정보에 따른 글 리스트 조회, 파라미터 없을시 기본값 1, 5")
	@Test
	void 글리스트조회() throws Exception {
		this.mockmvc.perform(get("/boards/list").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(print());
	}

	@DisplayName("카테고리 검색 결과에 따른 글 리스트 조회, 파라미터 없을시 기본값 1, 5")
	@Test
	void 글검색조회() throws Exception {
		this.mockmvc.perform(
				get("/boards/list?currentPage=2&category=title&search=2").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print());
	}

	@DisplayName("글 삭제 요청")
	@Test
	@Transactional
	void 글삭제() throws Exception {

		this.mockmvc.perform(delete("/boards/12").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent()).andDo(print());
	}

	@DisplayName("존재하지 않는 글 삭제 요청시 오류메세지 반환")
	@Test
	@Transactional
	void 글삭제_없는글() throws Exception {

		this.mockmvc.perform(delete("/boards/999").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andDo(print());
	}

	@DisplayName("공지사항 조회")
	@Test
	void 공지사항리스트조회() throws Exception {
		this.mockmvc.perform(get("/boards/notice").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(print());
	}

}
