package com.inhahackathon.foodmarket.auth.service;

import com.inhahackathon.foodmarket.auth.factory.OAuth2UserInfoFactory;
import com.inhahackathon.foodmarket.repository.OAuthUserRepository;
import com.inhahackathon.foodmarket.repository.UserInfoSetRepository;
import com.inhahackathon.foodmarket.repository.UserRepository;
import com.inhahackathon.foodmarket.type.dto.OAuthUserInfo;
import com.inhahackathon.foodmarket.type.dto.UserPrincipal;
import com.inhahackathon.foodmarket.type.entity.OAuthUser;
import com.inhahackathon.foodmarket.type.entity.User;
import com.inhahackathon.foodmarket.type.entity.UserInfoSet;
import com.inhahackathon.foodmarket.type.etc.OAuthProvider;
import com.inhahackathon.foodmarket.type.etc.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService  {

    private final OAuthUserRepository oAuthUserRepository;
    private final UserRepository userRepository;
    private final UserInfoSetRepository userInfoSetRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        return process(userRequest, oAuth2User);
    }

    OAuth2User process(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        OAuthProvider oAuthProvider = OAuthProvider.valueOf(
                userRequest.getClientRegistration().getClientName().toUpperCase());
        OAuthUserInfo oAuth2UserInfo = OAuth2UserInfoFactory.of(oAuthProvider, oAuth2User.getAttributes());
        OAuth2User user = saveOrUpDate(oAuth2UserInfo);
        return user;
    }

    OAuth2User saveOrUpDate(OAuthUserInfo oAuth2UserInfo) {
        Optional<OAuthUser> savedUser;
        savedUser = oAuthUserRepository.findByProviderUserIdAndOap(
                oAuth2UserInfo.getId(),
                OAuthProvider.valueOf(oAuth2UserInfo.getOAuthProviderName().toUpperCase())
        );
        User user;
        OAuthUser oAuthUser;
        if (savedUser.isPresent()) {
            oAuthUser = savedUser.get();
            user = oAuthUser.getUser();
        } else {
            User newUser = User.builder()
                    .uid("")
                    .name(oAuth2UserInfo.getName())
                    .profileImgUrl(oAuth2UserInfo.getPicture())
                    .role(Role.USER)
                    .provider(OAuthProvider.GOOGLE)
                    .build();
            user = userRepository.save(newUser);

            userInfoSetRepository.save(new UserInfoSet(user.getUserId()));

            oAuthUser = OAuthUser.builder()
                    .providerUserId(oAuth2UserInfo.getId())
                    .email(oAuth2UserInfo.getEmail())
                    .name(oAuth2UserInfo.getName())
                    .picture(oAuth2UserInfo.getPicture())
                    .oap(OAuthProvider.valueOf(oAuth2UserInfo.getOAuthProviderName().toUpperCase()))
                    .user(user)
                    .build();
        }
        oAuthUserRepository.save(oAuthUser);
        return UserPrincipal.create(user);
    }

}