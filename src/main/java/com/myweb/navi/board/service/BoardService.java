package com.myweb.navi.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.stereotype.Service;

import com.myweb.navi.advice.BusinessException;
import com.myweb.navi.board.dto.BoardListResponse;
import com.myweb.navi.board.dto.BoardResponse;
import com.myweb.navi.board.dto.BoardUpdateRequest;
import com.myweb.navi.board.dto.PostRequest;
import com.myweb.navi.board.exception.BoardNotFoundException;
import com.myweb.navi.board.exception.InvalidContentException;
import com.myweb.navi.board.exception.InvalidTitleException;
import com.myweb.navi.board.mapper.BoardMapper;
import com.myweb.navi.support.Pagination;
import com.myweb.navi.support.ValidateBoard;

@Service
public class BoardService {
	
	private final BoardMapper boardMapper;
	private final ValidatorFactory factory;
	
	public BoardService(BoardMapper boardMapper, ValidatorFactory factory) {
		this.boardMapper = boardMapper;
		this.factory = factory;
	}
	
	public void addBoard(PostRequest postRequest) {
		validateBoard(postRequest.getTitle(), postRequest.getContent());
		boardMapper.insertBoard(postRequest);
	}
	
	public BoardResponse findBoardByBno(Long bno) {
		BoardResponse boardResponse = boardMapper.selectBoardInfoByBno(bno);
		if(boardResponse == null || bno == null) {
			throw new BoardNotFoundException();
		}
		return boardResponse;
	}
	
	public void removeBoard(Long bno) {
		findBoardByBno(bno);
		boardMapper.deleteBoardByBno(bno);
	}
	
	public void modifyBoardByBno(BoardUpdateRequest boardUpdateRequest) {
		validateBoard(boardUpdateRequest.getTitle(), boardUpdateRequest.getContent());
		findBoardByBno(boardUpdateRequest.getBno());
		boardMapper.updateBoard(boardUpdateRequest);
	}
	
	public List<BoardResponse> findNoticeList() {
	    return boardMapper.selectNoticeList();
	}
	
	public Map<String, Object> findBoardListWithPagination(Integer currentPage, Integer postsPerPage, String category, String search) {
	    List<BoardListResponse> boardList = findBoardList(currentPage, postsPerPage, category, search);
	    Pagination pagination = findPagination(currentPage, postsPerPage, category, search);

	    Map<String, Object> response = new HashMap<>();
	    response.put("boardList", boardList);
	    response.put("pagination", pagination.getPageInfo());
	    return response;
	}
	
	public void addViewCount(Long bno) {
		boardMapper.updateviewCount(bno);
	}
	
	private List<BoardListResponse> findBoardList(Integer currentPage, Integer postsPerPage, String category, String search) {
	    Integer offset = (currentPage - 1) * postsPerPage;
	    return boardMapper.selectBoardList(offset, postsPerPage, category, search);
	}
	
	private Pagination findPagination(Integer currentPage, Integer postsPerPage, String category, String search) {
	    Integer totalPosts = findBoardCount(category, search);
	    return Pagination.builder()
	            .currentPage(currentPage)
	            .postsPerPage(postsPerPage)
	            .totalPosts(totalPosts)
	            .build();
	}
	
	private int findBoardCount(String category, String search) {
		return boardMapper.selectBoardCount(category, search);
	}
	
	private void validateBoard(String title, String content) {
		validateField("title", title, new InvalidTitleException());
		validateField("content", content, new InvalidContentException());
	}
	
	private void validateField(String fieldName, String fieldValue, BusinessException exception) {
		Validator validator = setUpValidator();
		Set<ConstraintViolation<ValidateBoard>> constraintViolations = validator.validateValue(ValidateBoard.class,
				fieldName, fieldValue);
		if (!constraintViolations.isEmpty()) {
			throw exception;
		}
	}
	
	private Validator setUpValidator() {
		return factory.getValidator();
	}
	
}
