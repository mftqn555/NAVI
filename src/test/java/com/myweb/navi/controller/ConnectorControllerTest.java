package com.myweb.navi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myweb.navi.connector.dto.BusNumRequest;
import com.myweb.navi.connector.dto.DustRequest;
import com.myweb.navi.connector.dto.LineIdRequest;
import com.myweb.navi.connector.dto.LocationRequest;
import com.myweb.navi.connector.dto.StationInfoRequest;
import com.myweb.navi.connector.dto.StationKeywordRequest;

@SpringBootTest
@AutoConfigureMockMvc
public class ConnectorControllerTest {

	@Autowired
	private MockMvc mockmvc;

	@Autowired
	private ObjectMapper objectMapper;

	@DisplayName("좌표정보로 날씨정보 조회")
	@Test
	void 날씨정보조회() throws Exception {
		LocationRequest request = LocationRequest.builder().nx("97").ny("76").build();

		this.mockmvc
				.perform(post("/api/weather").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print());
	}

	@DisplayName("좌표정보로 DB에 저장된 위치정보 조회")
	@Test
	void 위치정보조회() throws Exception {
		LocationRequest request = LocationRequest.builder().nx("97").ny("76").build();

		this.mockmvc
				.perform(post("/api/location").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print());
	}

	@DisplayName("시도정보로 미세먼지 정보 조회")
	@Test
	void 미세먼지정보조회() throws Exception {
		DustRequest request = new DustRequest("부산");

		this.mockmvc
				.perform(post("/api/dust").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print());
	}

	@DisplayName("키워드로 지하철역 검색")
	@Test
	void 지하철역검색() throws Exception {
		StationKeywordRequest request = new StationKeywordRequest("서면");

		this.mockmvc
				.perform(post("/api/station/keyword").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print());
	}

	@DisplayName("역아이디로 시간표 검색")
	@Test
	void 시간표검색() throws Exception {
		StationInfoRequest request = StationInfoRequest.builder().stationId("MTRARA1A01").upDownType("D").build();

		this.mockmvc
				.perform(post("/api/station/info").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print());
	}

	@DisplayName("버스 노선 검색하기")
	@Test
	void 버스노선검색() throws Exception {
		BusNumRequest request = new BusNumRequest("133");

		this.mockmvc
				.perform(post("/api/bus/num").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print());
	}

	@DisplayName("노선 ID로 위치정보 검색하기")
	@Test
	void 버스위치검색() throws Exception {
		LineIdRequest request = new LineIdRequest("5200033000");

		this.mockmvc
				.perform(post("/api/bus/location").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print());
	}

	@DisplayName("GPT 메세지 받아오기")
	@Test
	void GTP메세지() throws Exception {

		this.mockmvc
				.perform(
						get("/api/gpt").contentType(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print());
	}

}
