package com.myweb.navi.user.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.myweb.navi.user.dto.DeleteUserRequest;
import com.myweb.navi.user.dto.NicknameRequest;
import com.myweb.navi.user.dto.PasswordRequest;
import com.myweb.navi.user.dto.SignupRequest;
import com.myweb.navi.user.dto.UserResponse;

@Mapper
public interface UserMapper {

	@Insert("INSERT INTO USER(email, password, nickname)" + " VALUES(#{email}, #{password}, #{nickname})")
	void insertUser(SignupRequest signupRequest);

	@Select("SELECT email FROM user WHERE email=#{email}")
	String selectEmail(@Param("email") String email);

	@Select("SELECT nickname FROM user WHERE nickname=#{nickname}")
	String selectNickname(@Param("nickname") String nickname);

	@Select("SELECT password FROM user WHERE email=#{email}")
	String selectPassword(@Param("email") String email);

	@Select("SELECT * FROM user WHERE email=#{email}")
	UserResponse selectUserInfoByEmail(String email);

	@Select("SELECT * FROM user WHERE nickname=#{nickname}")
	UserResponse selectUserInfoByNickname(String nickname);

	@Update("UPDATE user SET password=#{newPassword} WHERE email=#{email} and password=#{oldPassword}")
	int updatePasswordByUserInfo(PasswordRequest passwordRequest);

	@Update("UPDATE user SET nickname=#{nickname} WHERE email=#{email}")
	int updateNicknameByEmail(NicknameRequest nicknameRequest);

	@Delete("DELETE FROM user WHERE email=#{email} and password=#{password}")
	int deleteUserByEmail(DeleteUserRequest deleteUserRequest);

}
