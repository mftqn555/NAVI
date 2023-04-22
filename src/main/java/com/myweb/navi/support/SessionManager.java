package com.myweb.navi.support;

import java.util.Arrays;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import com.myweb.navi.user.exception.UserNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SessionManager {
	
	public void createSession(Object value, HttpServletResponse response, HttpSession session) {
		// UUID를 통해 랜덤한 세션 ID 생성 후 세션(Redis)에 저장
		String sessionId = UUID.randomUUID().toString();
		session.setAttribute(sessionId, value);
		// 브라우저에 UUID값을 value로 갖는 쿠키 전송
		Cookie cookie = new Cookie(SessionConst.sessionId, sessionId);
		cookie.setHttpOnly(true);
		cookie.setSecure(true);
		cookie.setMaxAge(3600);
		response.addCookie(cookie);
		log.info("쿠키값 {}", cookie.getValue());
	}
	
	public Object getSession(HttpServletRequest request, HttpSession session) {
		// request에서 SessionConst.sessionId에 해당하는 쿠키 찾기
		Cookie cookie = findCookie(request, SessionConst.sessionId);
		if(cookie == null) {
			throw new UserNotFoundException();
		}
		// 쿠키의 값(UUID)에 해당하는 세션 찾은 후 리턴
		return session.getAttribute(cookie.getValue());
	}
	
	public void expire(HttpServletRequest request, HttpSession session) {
		// 로그인 쿠키정보가 있는지 확인
		Cookie cookie = findCookie(request, SessionConst.sessionId);
		if(cookie != null) {
			// 로그인 쿠키정보가 세션 및 쿠키 삭제
			session.removeAttribute(cookie.getValue());
			cookie.setMaxAge(0);
		}
	}
	
	public Cookie findCookie(HttpServletRequest request, String cookieName) {
        if (request.getCookies() == null) {
            return null;
        }

        return Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals(cookieName))
                .findAny()
                .orElse(null);
    }
	
}
