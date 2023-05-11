package com.myweb.navi.comment.controller;

import java.util.Map;

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

import com.myweb.navi.comment.dto.CommentRequest;
import com.myweb.navi.comment.dto.CommentUpdateRequest;
import com.myweb.navi.comment.service.CommentService;

@RestController
@RequestMapping("/comments")
public class CommentController {
	
	private final CommentService commentService;
	
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	// 댓글 쓰기
	@PostMapping
	public ResponseEntity<?> commentAdd(@RequestBody CommentRequest commentRequest) {
		commentService.addComment(commentRequest);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	// 대댓글 쓰기
	@PostMapping("/reply")
	public ResponseEntity<?> replyAdd(@RequestBody CommentRequest commentRequest) {
		commentService.addReply(commentRequest);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	// 댓글 수정
	@PatchMapping
	public ResponseEntity<?> modifyAdd(@RequestBody CommentUpdateRequest commentUpdateRequest) {
		commentService.modifyComment(commentUpdateRequest);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	// 댓글 리스트 조회 
	@GetMapping("/{bno}")
	public ResponseEntity<Map<String, Object>> commentList(@PathVariable Long bno,
															 @RequestParam(name = "currentPage", defaultValue = "1") Integer currentPage, 
															 @RequestParam(name = "postsPerPage", defaultValue = "10") Integer postsPerPage){
		Map<String, Object> response = commentService.findCommentListWithPagination(bno, currentPage, postsPerPage);
		return ResponseEntity.ok(response);
	}
	
	// 댓글 삭제
	@DeleteMapping("/{cno}")
	public ResponseEntity<?> commentRemove(@PathVariable Long cno) {
		commentService.removeCommentByBno(cno);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
