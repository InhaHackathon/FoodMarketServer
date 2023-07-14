package com.inhahackathon.foodmarket.service;

import com.google.common.base.Strings;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.inhahackathon.foodmarket.auth.jwt.AuthToken;
import com.inhahackathon.foodmarket.auth.jwt.AuthTokenProvider;
import com.inhahackathon.foodmarket.exception.NotAllowValueException;
import com.inhahackathon.foodmarket.exception.NotFoundException;
import com.inhahackathon.foodmarket.exception.SearchResultNotExistException;
import com.inhahackathon.foodmarket.repository.UserRepository;
import com.inhahackathon.foodmarket.type.dto.UserDto;
import com.inhahackathon.foodmarket.type.entity.User;
import com.inhahackathon.foodmarket.type.etc.OAuthProvider;
import com.inhahackathon.foodmarket.type.etc.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BoardService boardService;
    private final FirebaseApp firebaseApp;
    private final AuthTokenProvider authTokenProvider;
    private static final long TOKEN_DURATION = 1000L * 60L * 60L * 24L;

    @Transactional
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

    public UserDto getUser(Long userId) {
        User user = userRepository.getUserByUserId(userId).orElseThrow(() -> new NotFoundException("해당 유저가 없습니다."));
        UserDto userDto = UserDto.builder()
                .userId(userId)
                .name(user.getName())
                .location(user.getLocation())
                .profileImgUrl(user.getProfileImgUrl())
                .build();
        return userDto;
    }

    @Transactional
    public void updateUser(UserDto userDto) throws SearchResultNotExistException, NotAllowValueException {
        User user = userRepository.getUserByUserId(userDto.getUserId()).orElseThrow(() -> new NotFoundException("해당 유저가 없습니다."));
        if (!Strings.isNullOrEmpty(userDto.getName().trim())) {
            user.setName(userDto.getName().trim());
        } else {
            throw new NotAllowValueException();
        }
        if (!Strings.isNullOrEmpty(userDto.getLocation().trim())) {
            user.setLocation(userDto.getLocation().trim());
        }
        if (!Strings.isNullOrEmpty(userDto.getProfileImgUrl().trim())) {
            user.setProfileImgUrl(userDto.getProfileImgUrl().trim());
        }
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.getUserByUserId(userId).orElseThrow(() -> new NotFoundException("해당 유저가 없습니다."));
        boardService.deleteAllUserBoard(user);
        userRepository.deleteById(userId);
    }

    public AuthToken getUserToken(User user) {
        Date expiry = new Date();
        expiry.setTime(expiry.getTime() + (TOKEN_DURATION));

        AuthToken authToken = authTokenProvider.createToken(user.getName(), String.valueOf(user.getRole()), expiry);
        return authToken;
    }

}
