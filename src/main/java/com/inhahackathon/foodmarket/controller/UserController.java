package com.inhahackathon.foodmarket.controller;

import com.inhahackathon.foodmarket.auth.jwt.AuthToken;
import com.inhahackathon.foodmarket.auth.util.AuthUtil;
import com.inhahackathon.foodmarket.exception.NotAllowValueException;
import com.inhahackathon.foodmarket.exception.PermissionDeniedException;
import com.inhahackathon.foodmarket.service.UserService;
import com.inhahackathon.foodmarket.type.dto.ResponseModel;
import com.inhahackathon.foodmarket.type.dto.UserDto;
import com.inhahackathon.foodmarket.type.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User")
@Slf4j
public class UserController {

    private final UserService userService;

    @Operation(summary = "Firebase 유저 연동", description = "Firebase를 통해 가입한 유저 MariaDB 연동")
//    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/firebase/{uid}", method = RequestMethod.POST)
    public ResponseModel saveUserFromFirebase(@PathVariable String uid) {
        User user = userService.saveUserFromFirebase(uid);
        AuthToken authToken = userService.getUserToken(user);
        ResponseModel responseModel = ResponseModel.builder().build();
        responseModel.addData("jwt", authToken.getToken());
        return responseModel;
    }

    @Operation(summary = "유저 업데이트", description = "유저 정보 업데이트<br>" +
            "반드시 수정할 값만 전달할 것<br>" +
            "UserDto<br>" +
            "Long userId;<br>" +
            "String name;<br>" +
            "String location;<br>" +
            "String profileImgUrl;<br>")
    // @Secured({"ROLE_USER"})
    @PutMapping("/update")
    public ResponseModel userUpdate(
            @RequestBody UserDto userDto
    ) throws NotAllowValueException {
//        Long userId = AuthUtil.getAuthenticationInfoUserId();
        Long userId = 1L; // 임시
        userDto.setUserId(userId);
        userService.updateUser(userDto);
        ResponseModel responseModel = ResponseModel.builder().build();
        return responseModel;
    }

    @Operation(summary = "유저 삭제(일반 유저)", description = "회원 탈퇴" + "PathVariable : userId(삭제할 유저의 userId)")
//    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @DeleteMapping("/delete/{userId}")
    public ResponseModel userDeleteAsUser(
            @PathVariable("userId") Long userId
    ) throws PermissionDeniedException {
//        if (!userId.equals(AuthUtil.getAuthenticationInfoUserId())) {
//            throw new PermissionDeniedException();
//        }
        userService.deleteUser(userId);
        ResponseModel responseModel = ResponseModel.builder().build();
        return responseModel;
    }

}
