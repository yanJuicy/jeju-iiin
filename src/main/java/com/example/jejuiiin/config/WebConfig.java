package com.example.jejuiiin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	private static final String CORS_ALLOW_URL ="/api/**";
	private static final String REACT_LOCAL_HOST = "http://localhost:3000";
 	private static final String REACT_APP = "http://4team-troubleshooter.s3-website.ap-northeast-2.amazonaws.com/";
	private static final Long ACCESS_CONTROL_MAX_AGE_SECS = 600L;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping(CORS_ALLOW_URL)
				.allowCredentials(true)
				.maxAge(ACCESS_CONTROL_MAX_AGE_SECS)
				.allowedOrigins(REACT_LOCAL_HOST, REACT_APP)
				.allowedHeaders("*")
				.allowedMethods("*");
	}
}
