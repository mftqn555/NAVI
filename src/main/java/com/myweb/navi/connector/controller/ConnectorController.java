package com.myweb.navi.connector.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myweb.navi.connector.dto.BusNumRequest;
import com.myweb.navi.connector.dto.DustRequest;
import com.myweb.navi.connector.dto.LineIdRequest;
import com.myweb.navi.connector.dto.LocationRequest;
import com.myweb.navi.connector.dto.LocationResponse;
import com.myweb.navi.connector.dto.StationInfoRequest;
import com.myweb.navi.connector.dto.StationKeywordRequest;
import com.myweb.navi.connector.service.ConnectorService;

@RestController
@RequestMapping("/api")
public class ConnectorController {
	
	private final ConnectorService connectorService;
	
	public ConnectorController(ConnectorService connectorService) {
		this.connectorService = connectorService;
	}
	
	@PostMapping("/weather")
	public ResponseEntity<String> weatherDetails(@RequestBody LocationRequest locationRequest) throws Exception {
		String jsonResponse = connectorService.findWeatherInfo(locationRequest.getNx(), locationRequest.getNy()).toString();
		return ResponseEntity.ok(jsonResponse);
	}
	
	@PostMapping("/location")
	public ResponseEntity<?> locationDetails(@RequestBody LocationRequest locationRequest) {
		LocationResponse response = connectorService.findLocationByGridInfo(locationRequest.getNx(), locationRequest.getNy());
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/dust")
	public ResponseEntity<?> dustDetails(@RequestBody DustRequest dustRequest) throws Exception {
		String jsonResponse = connectorService.findDustInfo(dustRequest.getSidoName()).toString();
		return ResponseEntity.ok(jsonResponse);
	}
	
	@PostMapping("/station/keyword")
	public ResponseEntity<?> stationFind(@RequestBody StationKeywordRequest stationKeywordRequest) throws Exception {
		String jsonResponse = connectorService.findStationByKeyword(stationKeywordRequest.getKeyword()).toString();
		return ResponseEntity.ok(jsonResponse);
	}
	
	@PostMapping("/station/info")
	public ResponseEntity<?> stationInfoFind(@RequestBody StationInfoRequest stationInfoRequest) throws Exception {
		String jsonResponse = connectorService.findStationInfo(stationInfoRequest).toString();
		return ResponseEntity.ok(jsonResponse);
	}
	
	@PostMapping("/bus/num")
	public ResponseEntity<?> busNumfind(@RequestBody BusNumRequest busNumRequest) throws Exception {
		String response = connectorService.findBusNum(busNumRequest.getBusNum()).toString();
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/bus/location")
	public ResponseEntity<?> busLocationfind(@RequestBody LineIdRequest lineIdRequest) throws Exception {
		String response = connectorService.findBusLocation(lineIdRequest.getLineId());
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/gpt")
	public ResponseEntity<?> gptMessagefind() throws Exception {
		String response = connectorService.findGptMessage();
		return ResponseEntity.ok(response);
	}
	
}
