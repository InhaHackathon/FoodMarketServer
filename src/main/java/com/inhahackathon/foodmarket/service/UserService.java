package com.inhahackathon.foodmarket.service;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.inhahackathon.foodmarket.auth.jwt.AuthToken;
import com.inhahackathon.foodmarket.auth.jwt.AuthTokenProvider;
import com.inhahackathon.foodmarket.exception.NotFoundException;
import com.inhahackathon.foodmarket.repository.UserRepository;
import com.inhahackathon.foodmarket.type.entity.User;
import com.inhahackathon.foodmarket.type.etc.OAuthProvider;
import com.inhahackathon.foodmarket.type.etc.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FirebaseApp firebaseApp;
    private final AuthTokenProvider authTokenProvider;
    private static final long TOKEN_DURATION = 1000L * 60L * 60L * 24L;


    public User saveUserFromFirebase(String uid) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance(firebaseApp);
        UserRecord userRecord;
        try {
            userRecord = firebaseAuth.getUser(uid);
        } catch (FirebaseAuthException e) {
            throw new NotFoundException("Could not find user in Firebase");
        }

        User existingUser = userRepository.getUserByUid(uid);
        if (existingUser != null) {
            return existingUser;
        }

        User newUser = User.builder()
                .uid(userRecord.getUid())
                .name(userRecord.getDisplayName())
                .profileImgUrl(userRecord.getPhotoUrl())
                .role(Role.USER)
                .provider(OAuthProvider.GOOGLE)
                .build();
        userRepository.save(newUser);

        return newUser;
    }

    public AuthToken getUserToken(User user) {
        Date expiry = new Date();
        expiry.setTime(expiry.getTime() + (TOKEN_DURATION));

        AuthToken authToken = authTokenProvider.createToken(user.getName(), String.valueOf(user.getRole()), expiry);
        return authToken;
    }

}
