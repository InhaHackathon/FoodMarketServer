package com.inhahackathon.foodmarket.auth.jwt;

import com.inhahackathon.foodmarket.auth.exception.TokenValidateException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

public class AuthTokenProviderImpl implements AuthTokenProvider {

	private final Key key;

	public AuthTokenProviderImpl(String secret) {
		this.key = Keys.hmacShaKeyFor(secret.getBytes());
	}

	public AuthToken createToken(String id, String role, Date expiry) {
		return new AuthToken(id, role, expiry, key);
	}

	public AuthToken convertToken(String token) {
		return new AuthToken(token, key);
	}

	public Authentication getAuthentication(AuthToken authToken) {
		if (authToken.validate()) {
			Claims claims = authToken.getClaims();
			Collection<? extends GrantedAuthority> authorities = Arrays.stream(
							((String)claims.get("role")).split("\\|"))
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList());
			User user = new User((String)claims.get("id"), "[PROTECTED]", authorities);
			return new UsernamePasswordAuthenticationToken(user, authToken, authorities);
		} else {
			throw new TokenValidateException();
		}
	}

}