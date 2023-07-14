package com.inhahackathon.foodmarket.auth.factory;

import com.inhahackathon.foodmarket.type.dto.GoogleOAuthUserInfo;
import com.inhahackathon.foodmarket.type.dto.OAuthUserInfo;
import com.inhahackathon.foodmarket.type.etc.OAuthProvider;

import java.util.Map;

public class OAuth2UserInfoFactory {
	public static OAuthUserInfo of(OAuthProvider oAuthProvider, Map<String, Object> attributes) throws
		IllegalArgumentException {
		switch (oAuthProvider) {
			case GOOGLE:
				return new GoogleOAuthUserInfo(attributes);
			default:
				throw new IllegalArgumentException("OAuthProvider Not Excepted!!");
		}
	}
}
