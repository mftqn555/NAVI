package com.myweb.navi.board.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	@PostMapping("/post")
	public ResponseEntity<?> boardAdd(@RequestBody PostRequest postRequest) {
		boardService.addBoard(postRequest);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@PostMapping("/modify")
	public ResponseEntity<?> boardModify(@RequestBody BoardUpdateRequest boardUpdateRequest) {
		boardService.modifyBoardByBno(boardUpdateRequest);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{bno}")
	public ResponseEntity<BoardResponse> boardfind(@PathVariable Long bno){
		BoardResponse boardResponse = boardService.findBoardByBno(bno);
		return ResponseEntity.ok(boardResponse);
	}
	
	@PostMapping("/delete/{bno}")
	public ResponseEntity<?> boardRemove(@PathVariable Long bno) {
		boardService.removeBoard(bno);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
}
