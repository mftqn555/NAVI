package com.myweb.navi.connector.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.myweb.navi.connector.dto.LocationResponse;

@Mapper
public interface ConnectorMapper {

	@Select("SELECT adress1 from location where nx=#{nx} and ny=#{ny} limit 1")
	LocationResponse selectLocationByGridInfo(String nx, String ny);
	
}
