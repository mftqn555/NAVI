package com.myweb.navi.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import com.myweb.navi.support.SessionManager;
import com.myweb.navi.user.exception.LoginFailedException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		SessionManager sessionManager = new SessionManager();
		String requestURI = request.getRequestURI();
		String requestMethod = request.getMethod();
		
		if(requestMethod.equals("OPTIONS")) {
			return true;
		}
		
		log.info("인증 체크 인터셉터 실행 [{}] [{}]", requestURI, requestMethod);
		HttpSession session = request.getSession(false);
		
		try {
			sessionManager.getSession(request, session);
		} catch (RuntimeException e) {
			log.info("미인증 사용자 요청");
			throw new LoginFailedException();
		} 
		return true;
	}
	
}