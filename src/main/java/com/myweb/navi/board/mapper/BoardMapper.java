package com.myweb.navi.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.myweb.navi.board.dto.BoardResponse;
import com.myweb.navi.board.dto.BoardUpdateRequest;
import com.myweb.navi.board.dto.PostRequest;

@Mapper
public interface BoardMapper {
	
	// 글 쓰기
	@Insert("INSERT INTO board(user_id, title, content, nickname, image_url) "
			+ "VALUES(#{user_id}, #{title}, #{content}, #{nickname}, #{image_url})")
	void insertBoard(PostRequest postRequest); 
	
	// 글 수정
	@Update("UPDATE board "
			+ "SET title=#{title}, content=#{content}, nickname=#{nickname}, image_url=#{image_url} "
			+ "WHERE bno=#{bno}")
	void updateBoard(BoardUpdateRequest boardUpdateRequest);
	
	// 글 조회
	@Select("SELECT bno, title, content, view_count, create_date, nickname, image_url FROM board WHERE bno=#{bno}")
	BoardResponse selectBoardInfoByBno(Long bno);
	
	// 글 리스트 조회
	@Select("SELECT bno, title, content, view_count, create_date, nickname, image_url from board " +
	        "ORDER BY bno desc " +
	        "LIMIT #{offset}, #{page_size}")
	List<BoardResponse> selectBoardList(Long page_number, Long page_size);
	
	// 글 카운팅
	@Select("SELECT COUNT(*) from board")
	Long selectBoardCount();
	
	// 글 삭제
	@Delete("DELETE FROM board WHERE bno=#{bno}")
	void deleteBoardByBno(Long bno);
	
	
	
}
