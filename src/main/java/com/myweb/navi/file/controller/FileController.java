package com.myweb.navi.file.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.myweb.navi.file.dto.UploadResponse;
import com.myweb.navi.support.S3Uploader;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class FileController {

	private final S3Uploader s3Uploader;
	
	// 파일 업로드
	// 파일 최소 최대사이즈 지정(안지킬시 오류메세지 반환 해야됨)
	@PostMapping("/upload")
	public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) throws IOException {
		String url = s3Uploader.upload(file, "navi-board");
		UploadResponse uploadResponse = UploadResponse.builder()
				.uploaded(1)
				.fileName(file.getOriginalFilename())
				.url(url)
				.build();
		return ResponseEntity.ok(uploadResponse);
	}

}
