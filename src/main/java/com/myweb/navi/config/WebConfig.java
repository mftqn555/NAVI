package com.myweb.navi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("http://localhost:8080", "http://localhost:8082")
				.allowedMethods("GET", "POST")
				.allowCredentials(true)
				.maxAge(3600);
		WebMvcConfigurer.super.addCorsMappings(registry);
	}
}