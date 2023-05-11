package com.myweb.navi.comment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.myweb.navi.comment.dto.CommentRequest;
import com.myweb.navi.comment.dto.CommentResponse;
import com.myweb.navi.comment.dto.CommentUpdateRequest;

@Mapper
public interface CommentMapper {

//	- 댓글 조회 
//	- 댓글 쓰기
//	- 댓글 삭제
//	- 댓글 수정
//  - 답글 쓰기, 수정, 삭제
	
	// 댓글 쓰기
	@Insert("INSERT INTO comment(bno, user_id, content, nickname, re_cno) "
			+ "VALUES(#{bno}, #{user_id}, #{content}, #{nickname}, #{re_cno})")
	void insertComment(CommentRequest commentRequest);

	// 댓글 조회
	@Select("SELECT cno, bno, user_id, nickname, content, DATE_FORMAT(create_date, '%y.%m.%d. %H:%i') as create_date, " + 
			"IF(re_cno IS NULL, cno, re_cno) AS re_cno " +
	        "FROM comment " +
	        "WHERE bno = #{bno} " +
	        "ORDER BY re_cno asc " +
	        "LIMIT #{offset}, #{postsPerPage}")
	List<CommentResponse> selectCommentList(Long bno, Integer offset, Integer postsPerPage);
	
	// 댓글 번호로 조회
	@Select("SELECT * FROM comment WHERE cno=#{cno}")
	CommentResponse selectCommentByCno(Long cno);
	
	// 댓글 카운팅
	@Select("SELECT COUNT(*) FROM comment WHERE bno=#{bno}")
	int selectCommentCountByBno(Long bno);
	
	// 댓글 삭제
	@Delete("DELETE FROM comment WHERE cno=#{cno}")
	void deleteCommentByCno(Long cno);
	
	// 댓글 수정
	@Update("UPDATE comment SET content=#{content} WHERE cno=#{cno}")
	void updateCommentByCno(CommentUpdateRequest commentUpdateRequest);
	
}
