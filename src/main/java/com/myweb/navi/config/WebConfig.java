package com.myweb.navi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("http://localhost:8080", "http://localhost:8082")
				.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE").allowCredentials(true).maxAge(3600);
		WebMvcConfigurer.super.addCorsMappings(registry);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(new LoginCheckInterceptor()).order(1).addPathPatterns("/**").excludePathPatterns("/",
				"/users/signup", "/users/login", "/users/logout", "/boards/list", "/boards/count", "/boards/{bno}", "/*.ico",
				"/comments", "/error", "/users/exist", "/users/email", "/comments/{bno}", "/boards/{bno}/view", "/api/weather", "/api/location", "/api/dust",
				"/gpt", "/api/station/keyword", "/api/station/info", "/api/bus/num", "/api/bus/location", "/api/gpt");
	}

}
