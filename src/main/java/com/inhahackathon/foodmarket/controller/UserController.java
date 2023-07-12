package com.inhahackathon.foodmarket.controller;

import com.inhahackathon.foodmarket.auth.jwt.AuthToken;
import com.inhahackathon.foodmarket.service.UserService;
import com.inhahackathon.foodmarket.type.dto.ResponseModel;
import com.inhahackathon.foodmarket.type.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User")
@Slf4j
public class UserController {

    private final UserService userService;

    @Operation(summary = "Firebase 유저 연동", description = "Firebase를 통해 가입한 유저 MariaDB 연동" +
            "\n PathVariable : uid(Firebase uid)")
//    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/firebase/{uid}", method = RequestMethod.POST)
    public ResponseModel saveUserFromFirebase(@PathVariable String uid) {
        User user = userService.saveUserFromFirebase(uid);
        AuthToken authToken = userService.getUserToken(user);
        log.debug("AuthToken Data : {}", authToken.getToken());
        ResponseModel responseModel = ResponseModel.builder().build();
        responseModel.addData("user", user);
        responseModel.addData("jwt", authToken.getToken());
        return responseModel;
    }

}
