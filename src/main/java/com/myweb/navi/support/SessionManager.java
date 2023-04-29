package com.myweb.navi.support;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

	public void createSession(Object obj, HttpServletResponse response, HttpSession session) {
		String sessionId = UUID.randomUUID().toString();
		byte[] serializedObj = serialize(obj);
		session.setAttribute(sessionId, serializedObj);
		
		Cookie cookie = new Cookie(SessionConst.sessionId, sessionId);
		cookie.setHttpOnly(true);
		cookie.setSecure(true);
		cookie.setPath("/");
		response.addCookie(cookie);
		log.info("쿠키값 {}", cookie.getValue());
	}

	public Object getSession(HttpServletRequest request, HttpSession session) {

		Cookie cookie = findCookie(request, SessionConst.sessionId);
		if (cookie == null) {
			throw new UserNotFoundException();
		}

		return deserialize((byte[]) session.getAttribute(cookie.getValue()));
	}

	public void expire(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		Cookie cookie = findCookie(request, SessionConst.sessionId);
		if (cookie != null) {
			session.removeAttribute(cookie.getValue());
			cookie.setHttpOnly(true);
			cookie.setSecure(true);
			cookie.setPath("/");
			cookie.setMaxAge(0);
			cookie.setValue(null);
			response.addCookie(cookie);
			log.info("쿠키 삭제됨");
		}
	}

	public Cookie findCookie(HttpServletRequest request, String cookieName) {
		if (request.getCookies() == null) {
			return null;
		}
		
		for(Cookie cookie : request.getCookies()) {
			if(cookie.getName().equals(cookieName)) {
				log.info(cookie.getName());
				log.info(cookie.getValue());
				return cookie;
			}
		}
		return null;
	}

	private byte[] serialize(Object obj) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.flush();
			return bos.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
	
	private Object deserialize(byte[] data) {
        try {
            return new ObjectInputStream(new ByteArrayInputStream(data)).readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException();
        }
    }

}
