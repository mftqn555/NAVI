package com.myweb.navi.board.controller;

import java.io.IOException;
import java.util.List;
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
import org.springframework.web.multipart.MultipartFile;

import com.myweb.navi.board.dto.BoardResponse;
import com.myweb.navi.board.dto.BoardUpdateRequest;
import com.myweb.navi.board.dto.PostRequest;
import com.myweb.navi.board.dto.UploadResponse;
import com.myweb.navi.board.service.BoardService;
import com.myweb.navi.support.S3Uploader;

@RestController
@RequestMapping("/boards")
public class BoardController {

	private final BoardService boardService;
	private final S3Uploader s3Uploader;

	public BoardController(BoardService boardService, S3Uploader s3Uploader) {
		this.boardService = boardService;
		this.s3Uploader = s3Uploader;
	}

	// 파일 업로드
	@PostMapping("/upload")
	public ResponseEntity<?> upload(@RequestParam("upload") MultipartFile file) throws IOException {
		String url = s3Uploader.upload(file, "navi-board");
		UploadResponse uploadResponse = UploadResponse.builder().uploaded(1).fileName(file.getOriginalFilename())
				.url(url).build();
		return ResponseEntity.ok(uploadResponse);
	}

	// 게시글 쓰기
	@PostMapping
	public ResponseEntity<?> boardAdd(@RequestBody PostRequest postRequest) {
		boardService.addBoard(postRequest);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	// 게시글 수정
	@PatchMapping("/{bno}")
	public ResponseEntity<?> boardModify(@RequestBody BoardUpdateRequest boardUpdateRequest, @PathVariable Long bno) {
		boardUpdateRequest.setBno(bno);
		boardService.modifyBoardByBno(boardUpdateRequest);
		return ResponseEntity.noContent().build();
	}

	// 게시글 조회
	@GetMapping("/{bno}")
	public ResponseEntity<BoardResponse> boardfind(@PathVariable Long bno) {
		BoardResponse boardResponse = boardService.findBoardByBno(bno);
		return ResponseEntity.ok(boardResponse);
	}

	// 게시글 조회수 증가
	@PostMapping("/{bno}/view")
	public ResponseEntity<?> viewCountAdd(@PathVariable Long bno) {
		boardService.addViewCount(bno);
		return ResponseEntity.ok().build();
	}

	// 게시글 전체 조회
	@GetMapping("/list")
	public ResponseEntity<Map<String, Object>> boardList(
			@RequestParam(name = "currentPage", defaultValue = "1") Integer currentPage,
			@RequestParam(name = "postsPerPage", defaultValue = "5") Integer postsPerPage,
			@RequestParam(name = "category", defaultValue = "title") String category,
			@RequestParam(name = "search", defaultValue = "") String search) {
		Map<String, Object> response = boardService.findBoardListWithPagination(currentPage, postsPerPage, category,
				search);
		return ResponseEntity.ok(response);
	}

	// 공지사항 조회
	@GetMapping("/notice")
	public ResponseEntity<List<BoardResponse>> noticeList() {
		return ResponseEntity.ok(boardService.findNoticeList());
	}

	// 게시글 삭제
	@DeleteMapping("/{bno}")
	public ResponseEntity<?> boardRemove(@PathVariable Long bno) {
		boardService.removeBoard(bno);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
