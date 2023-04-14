package com.myweb.navi.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlers {
	
	// 오류의 갯수가 다수일경우 
	//	다수의 메세지중 랜덤으로 첫번째에 할당된 fieldError의 defaultMessage의 메세지만 출력됨
	//  - 검증기능 자체엔 문제없음, 에러메세지가 랜덤으로 나오는 대로 프론트에서 경고메세지 출력하면 될듯함
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentException(BindingResult bindingResult) {
		String errorMessage = bindingResult.getFieldErrors()
                .get(0)
                .getDefaultMessage();
       return ResponseEntity.badRequest().body(new ErrorResponse(errorMessage));
	}
	
	@ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
	}

}
