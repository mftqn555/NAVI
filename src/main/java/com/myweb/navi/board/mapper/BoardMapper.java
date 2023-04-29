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
	@Insert("INSERT INTO board(user_id, title, content, nickname) "
			+ "VALUES(#{user_id}, #{title}, #{content}, #{nickname})")
	void insertBoard(PostRequest postRequest); 
	
	// 글 수정
	@Update("UPDATE board "
			+ "SET title=#{title}, content=#{content} "
			+ "WHERE bno=#{bno}")
	void updateBoard(BoardUpdateRequest boardUpdateRequest);
	
	// 글 조회
	@Select("SELECT bno, title, content, view_count, create_date, nickname FROM board WHERE bno=#{bno}")
	BoardResponse selectBoardInfoByBno(Long bno);
	
	// 글 리스트 조회
	@Select("SELECT bno, title, view_count, DATE_FORMAT(create_date, '%y.%m.%d/%h:%m') as create_date, nickname from board " +
	        "ORDER BY bno desc " +
	        "LIMIT #{offSet}, #{postsPerPage}")
	List<BoardResponse> selectBoardList(Integer offSet, Integer postsPerPage);
	
	// 글 카운팅
	@Select("SELECT COUNT(*) from board")
	int selectBoardCount();
	
	// 글 삭제
	@Delete("DELETE FROM board WHERE bno=#{bno}")
	void deleteBoardByBno(Long bno);
	
}
