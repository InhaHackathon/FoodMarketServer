package com.inhahackathon.foodmarket.config;

import com.inhahackathon.foodmarket.auth.jwt.AuthTokenProvider;
import com.inhahackathon.foodmarket.auth.jwt.AuthTokenProviderImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

	@Value("${jwt.secret-key}")
	private String secretKey;

	@Bean
	public AuthTokenProvider authTokenProvider() {
		return new AuthTokenProviderImpl(secretKey);
	}

}
