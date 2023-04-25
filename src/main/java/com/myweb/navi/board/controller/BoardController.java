package com.myweb.navi.board.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myweb.navi.board.dto.BoardResponse;
import com.myweb.navi.board.dto.BoardUpdateRequest;
import com.myweb.navi.board.dto.PostRequest;
import com.myweb.navi.board.service.BoardService;

@RestController
@RequestMapping("/boards")
public class BoardController {
	
	private final BoardService boardService;
	
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	// 게시글 쓰기
	@PostMapping
	public ResponseEntity<?> boardAdd(@RequestBody PostRequest postRequest) {
		boardService.addBoard(postRequest);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	// 게시글 수정
	@PatchMapping
	public ResponseEntity<?> boardModify(@RequestBody BoardUpdateRequest boardUpdateRequest) {
		boardService.modifyBoardByBno(boardUpdateRequest);
		return ResponseEntity.noContent().build();
	}
	
	// 게시글 조회
	@GetMapping("/{bno}")
	public ResponseEntity<BoardResponse> boardfind(@PathVariable Long bno){
		BoardResponse boardResponse = boardService.findBoardByBno(bno);
		return ResponseEntity.ok(boardResponse);
	}
	
	// 게시글 전체 조회
	@GetMapping("/list")
	public ResponseEntity<List<BoardResponse>> boardList(@RequestParam(name = "page_number", defaultValue = "0") Long page_number, 
			 											 @RequestParam(name = "page_size", defaultValue = "5") Long page_size){
		Long offset = null;
        if (page_number != null && page_size != null) {
            offset = (page_number - 1) * page_size;
        }
		List<BoardResponse> boardList = boardService.findBoardList(offset, page_size);
		return ResponseEntity.ok(boardList);
	}
	
	// 게시글 전체 갯수 카운트
	@GetMapping("/count")
	public ResponseEntity<?> boardCount(){
		return ResponseEntity.ok(boardService.findBoardCount());
	}
	
	// 게시글 삭제
	@DeleteMapping("/{bno}")
	public ResponseEntity<?> boardRemove(@PathVariable Long bno) {
		boardService.removeBoard(bno);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
}
