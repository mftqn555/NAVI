package com.myweb.navi.comment.controller;

import java.util.List;

import javax.validation.Valid;

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
import com.myweb.navi.comment.dto.CommentResponse;
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
	public ResponseEntity<?> commentAdd(@Valid @RequestBody CommentRequest commentRequest) {
		commentService.addComment(commentRequest);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	// 대댓글 쓰기
	@PostMapping("/reply")
	public ResponseEntity<?> replyAdd(@Valid @RequestBody CommentRequest commentRequest) {
		commentService.addReply(commentRequest);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	// 댓글 수정
	@PatchMapping
	public ResponseEntity<?> modifyAdd(@Valid @RequestBody CommentUpdateRequest commentUpdateRequest) {
		commentService.modifyComment(commentUpdateRequest);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	// 댓글 리스트 조회 
	@GetMapping
	public ResponseEntity<List<CommentResponse>> commentList(@RequestParam(name = "bno") Long bno,
															 @RequestParam(name = "page_number", defaultValue = "0") Long page_number, 
															 @RequestParam(name = "page_size", defaultValue = "10") Long page_size){
		Long offset = null;
        if (page_number != null && page_size != null) {
            offset = (page_number - 1) * page_size;
        }
		List<CommentResponse> commentList = commentService.findCommentListByBno(bno, offset, page_size);
		return ResponseEntity.ok(commentList);
	}
	
	// 댓글 수 카운트
	@GetMapping("/count")
	public ResponseEntity<?> commentCount(Long bno) {
		return ResponseEntity.ok(commentService.findCommentCountByBno(bno));
	}
	
	// 댓글 삭제
	@DeleteMapping("/{cno}")
	public ResponseEntity<?> commentRemove(@PathVariable Long cno) {
		commentService.removeCommentByBno(cno);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
