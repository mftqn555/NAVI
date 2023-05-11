package com.myweb.navi.connector.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.myweb.navi.connector.dto.LocationResponse;
import com.myweb.navi.connector.dto.StationInfoRequest;
import com.myweb.navi.connector.mapper.ConnectorMapper;

@Service
public class ConnectorService {
	
	@Value("${public.api.key}")
	private String publicKey;
	
	@Value("${openapi.chatgpt.key}")
	private String gptKey;
	
	private final ConnectorMapper connectorMapper;
	
	public ConnectorService(ConnectorMapper connectorMapper) {
		this.connectorMapper = connectorMapper;
	}
	
	public LocationResponse findLocationByGridInfo(String nx, String ny) {
		return connectorMapper.selectLocationByGridInfo(nx, ny);
	}
	
	public String findGptMessage() throws Exception {
		URL url = new URL("https://api.openai.com/v1/chat/completions");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Bearer " + gptKey);
		conn.setReadTimeout(30000);
		conn.setConnectTimeout(30000);
        conn.setDoOutput(true);

        String requestBody = setGptMessage();
        try(OutputStream os = conn.getOutputStream()) {
            byte[] input = requestBody.getBytes("utf-8");
            os.write(input, 0, input.length);			
        }

        int responseCode = conn.getResponseCode();
        BufferedReader inputReader;
        if (responseCode >= 400) {
            inputReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        } else {
            inputReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        }
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = inputReader.readLine()) != null) {
            response.append(inputLine);
        }
        inputReader.close();

        return response.toString();
    }

	
	public String findBusLocation(String lineId) throws Exception {
		
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/6260000/BusanBIMS/busInfoByRouteId");
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + publicKey);
        urlBuilder.append("&" + URLEncoder.encode("lineid","UTF-8") + "=" + URLEncoder.encode(lineId, "UTF-8"));
        
        URL url = new URL(urlBuilder.toString());
        
		return getResponse(url);
	}
	
	public String findBusNum(String busNum) throws Exception {
		
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/6260000/BusanBIMS/busInfo");
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + publicKey);
        urlBuilder.append("&" + URLEncoder.encode("lineno","UTF-8") + "=" + URLEncoder.encode(busNum, "UTF-8"));
        
        URL url = new URL(urlBuilder.toString());

		return getResponse(url);
	}
	
	public String findStationInfo(StationInfoRequest request) throws Exception {
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/SubwayInfoService/getSubwaySttnAcctoSchdulList");
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + publicKey);
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("999", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("subwayStationId","UTF-8") + "=" + URLEncoder.encode(request.getStationId(), "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("dailyTypeCode","UTF-8") + "=" + URLEncoder.encode(weekFormat(), "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("upDownTypeCode","UTF-8") + "=" + URLEncoder.encode(request.getUpDownType(), "UTF-8"));
        
        URL url = new URL(urlBuilder.toString());

        return getResponse(url);
	}
	
	public String findStationByKeyword(String keyword) throws Exception {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/SubwayInfoService/getKwrdFndSubwaySttnList");
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + publicKey);
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("subwayStationName","UTF-8") + "=" + URLEncoder.encode(keyword, "UTF-8"));
        
        URL url = new URL(urlBuilder.toString());

		return getResponse(url);
	}
	
	public String findDustInfo(String sidoName) throws Exception {
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + publicKey);
        urlBuilder.append("&" + URLEncoder.encode("returnType","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("sidoName","UTF-8") + "=" + URLEncoder.encode(sidoName, "UTF-8")); 
        urlBuilder.append("&" + URLEncoder.encode("ver","UTF-8") + "=" + URLEncoder.encode("1.3", "UTF-8"));

        URL url = new URL(urlBuilder.toString());

		return getResponse(url);
	}
	
	public String findWeatherInfo(String nx, String ny) throws Exception {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst");
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + publicKey);
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("290", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(dateFormat(), "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode("0200", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(nx, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(ny, "UTF-8"));
        URL url = new URL(urlBuilder.toString());

        return getResponse(url);
	}
	
	private String getResponse(URL url) throws Exception {
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setReadTimeout(5000);
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");

		BufferedReader rd;

		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}

		StringBuilder sb = new StringBuilder();
		String line;

		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}

		rd.close();
		conn.disconnect();
		return sb.toString();
	}
	
	private String setGptMessage() {
		StringBuilder body = new StringBuilder();
		body.append("{ \"model\": \"gpt-3.5-turbo\", ");
		body.append("\"messages\": [{\"role\": \"system\", ");
		body.append("\"content\": \"방문하시는 분들께 보여드릴 힘이되는 ");
		body.append(dayFormat());
		body.append(" 인사문구를 80자로 작성해줘. 만약 안녕하세요로 시작한다면 안녕하세요를 생략해줘\"}], ");
		body.append("\"max_tokens\": 2000 }");

		return body.toString();
	}
	
	private String dayFormat() {
		Date currentTime = new Date();

        int currentHour = Integer.parseInt(new SimpleDateFormat("HH").format(currentTime));

        if (currentHour >= 0 && currentHour < 6) {
            return "새벽";
        } else if (currentHour >= 6 && currentHour < 12) {
        	return "아침";
        } else if (currentHour >= 12 && currentHour < 18) {
        	return "오후";
        } else {
        	return "저녁";
        }

	}
	
	private String dateFormat() {
        Date nowDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDate);

        if (calendar.get(Calendar.HOUR_OF_DAY) < 3) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }
    

        Date formattedDate = calendar.getTime();
        return simpleDateFormat.format(formattedDate);
	}
	
	private String weekFormat() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        if (dayOfWeek >= Calendar.MONDAY && dayOfWeek <= Calendar.FRIDAY) {
            return "01";
        } else if (dayOfWeek == Calendar.SATURDAY) {
            return "02";
        } else if (dayOfWeek == Calendar.SUNDAY) {
            return "03";
        } else {
            return "";
        }
    }

	
}
