package com.myweb.navi.user.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.myweb.navi.user.dto.SignupRequest;
import com.myweb.navi.user.dto.UserResponse;

@Mapper
public interface UserMapper {
	
	@Insert("INSERT INTO USER(email, password, nickname)"
		 + " VALUES(#{email}, #{password}, #{nickname})")
	void insertUser(SignupRequest signupRequest);
	
	@Select("SELECT email FROM user WHERE email=#{email}")
	String selectEmail(@Param("email") String email);
	
	@Select("SELECT nickname FROM user WHERE nickname=#{nickname}")
	String selectNickname(@Param("nickname") String nickname);
	
	@Select("SELECT * FROM user WHERE id=#{id}")
	UserResponse selectUserInfoById(Long id);
	
}
