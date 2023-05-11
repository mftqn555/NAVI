package com.myweb.navi.comment.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.stereotype.Service;

import com.myweb.navi.advice.BusinessException;
import com.myweb.navi.board.service.BoardService;
import com.myweb.navi.comment.dto.CommentRequest;
import com.myweb.navi.comment.dto.CommentResponse;
import com.myweb.navi.comment.dto.CommentUpdateRequest;
import com.myweb.navi.comment.exception.CommentNotFoundException;
import com.myweb.navi.comment.exception.InvalidCommentException;
import com.myweb.navi.comment.mapper.CommentMapper;
import com.myweb.navi.support.Pagination;
import com.myweb.navi.support.ValidateComment;

@Service
public class CommentService {
	
	// 유효성 검사 추가로 할거 확인
	
	private final CommentMapper commentMapper;
	private final BoardService boardService;
	private final ValidatorFactory factory;
	
	public CommentService(CommentMapper commentMapper, BoardService boardService, ValidatorFactory factory) {
		this.commentMapper = commentMapper;
		this.boardService = boardService;
		this.factory = factory;
	}

	// 댓글 쓰기
	public void addComment(CommentRequest commentRequest) {
		validateField("content", commentRequest.getContent(), new InvalidCommentException());
		commentMapper.insertComment(commentRequest);
	}
	
	// 답글 쓰기
	public void addReply(CommentRequest commentRequest) {
		validateField("content", commentRequest.getContent(), new InvalidCommentException());
		commentMapper.insertComment(commentRequest);
	}
	
	// 댓글 수정
	public void modifyComment(CommentUpdateRequest commentUpdateRequest) {
		isCommentNull(commentUpdateRequest.getCno());
		validateField("content", commentUpdateRequest.getContent(), new InvalidCommentException());
		commentMapper.updateCommentByCno(commentUpdateRequest);
	}
	
	// 댓글 삭제
	public void removeCommentByBno(Long cno) {
		isCommentNull(cno);
		commentMapper.deleteCommentByCno(cno);
	}
	
	// 댓글 리스트 조회
	public Map<String, Object> findCommentListWithPagination(Long bno, Integer currentPage, Integer postsPerPage) {
		List<CommentResponse> commentList = findCommentListByBno(bno, currentPage, postsPerPage);
		Pagination pagination = findPagination(bno, currentPage, postsPerPage);
		
		Map<String, Object> response = new HashMap<>();
		response.put("pagination", pagination.getPageInfo());
		response.put("commentList", commentList);
		return response;
	}
	
	private List<CommentResponse> findCommentListByBno(Long bno, Integer currentPage, Integer postsPerPage) {
		boardService.findBoardByBno(bno);
		Integer offset = (currentPage - 1) * postsPerPage;
		return commentMapper.selectCommentList(bno, offset, postsPerPage);
	}
	
	private Pagination findPagination(Long bno, Integer currentPage, Integer postsPerPage) {
		Integer totalComments = findCommentCountByBno(bno);
		return Pagination.builder()
				.currentPage(currentPage)
				.postsPerPage(postsPerPage)
				.totalPosts(totalComments)
				.build();
	}
	
	// 댓글 카운팅
	private int findCommentCountByBno(Long bno) {
		boardService.findBoardByBno(bno);
		return commentMapper.selectCommentCountByBno(bno);
	}
	
	private void isCommentNull(Long cno) {
		if(commentMapper.selectCommentByCno(cno) == null) {
			throw new CommentNotFoundException();
		}
	}
	
	private void validateField(String fieldName, String fieldValue, BusinessException exception) {
		Validator validator = setUpValidator();
		Set<ConstraintViolation<ValidateComment>> constraintViolations = validator.validateValue(ValidateComment.class,
				fieldName, fieldValue);
		if (!constraintViolations.isEmpty()) {
			throw exception;
		}
	}
	
	private Validator setUpValidator() {
		return factory.getValidator();
	}

}
