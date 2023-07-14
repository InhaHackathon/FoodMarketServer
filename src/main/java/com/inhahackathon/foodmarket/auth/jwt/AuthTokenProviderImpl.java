package com.inhahackathon.foodmarket.auth.jwt;

import com.inhahackathon.foodmarket.auth.exception.TokenValidateException;
import com.inhahackathon.foodmarket.type.dto.UserPrincipal;
import com.inhahackathon.foodmarket.type.etc.OAuthProvider;
import com.inhahackathon.foodmarket.type.etc.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

public class AuthTokenProviderImpl implements AuthTokenProvider {

	private final Key key;

	public AuthTokenProviderImpl(String secret) {
		this.key = Keys.hmacShaKeyFor(secret.getBytes());
	}

	public AuthToken createToken(UserPrincipal userPrincipal, Date expiry) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("userId", userPrincipal.getUserId().toString());
		claims.put("uid", userPrincipal.getUid());
		claims.put("name", userPrincipal.getName());
		claims.put("profileImgUrl", userPrincipal.getProfileImgUrl());
		claims.put("role", userPrincipal.getRole().name());
		claims.put("provider", userPrincipal.getProvider().name());

		return new AuthToken(claims, expiry, key);
	}

	@Override
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

			Long userId = Long.valueOf((String) claims.get("userId"));
			String uid = (String) claims.get("uid");
			String name = (String) claims.get("name");
			String location = (String) claims.get("location");
			String profileImgUrl = (String) claims.get("profileImgUrl");
			Role role = Role.valueOf((String) claims.get("role"));
			OAuthProvider provider = OAuthProvider.valueOf((String) claims.get("provider"));

			UserPrincipal principal = new UserPrincipal(userId, uid, name, location, profileImgUrl, role, provider, (Collection<GrantedAuthority>) authorities, new HashMap<>());
			return new UsernamePasswordAuthenticationToken(principal, authToken, authorities);
		} else {
			throw new TokenValidateException();
		}
	}

}