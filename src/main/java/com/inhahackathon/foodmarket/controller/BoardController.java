package com.inhahackathon.foodmarket.controller;

import com.inhahackathon.foodmarket.auth.util.AuthUtil;
import com.inhahackathon.foodmarket.repository.UserRepository;
import com.inhahackathon.foodmarket.service.BoardService;
import com.inhahackathon.foodmarket.type.dto.BoardRequestDto;
import com.inhahackathon.foodmarket.type.dto.BoardResponseDto;
import com.inhahackathon.foodmarket.type.dto.ResponseModel;
import com.inhahackathon.foodmarket.type.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
@Tag(name = "Board")
@Slf4j
public class BoardController {

    private final BoardService boardService;
    private final UserRepository userRepository;

    @Operation(summary = "게시글 등록", description = "게시글 등록<br>" +
            "BoardRequestDto<br>" +
            "String productName(not null);<br>" +
            "String productImg;<br>" +
            "LocalDate expirationDate(not null);<br>" +
            "Long price(not null);<br> +" +
            "String description;")
    @PostMapping("/create")
    public ResponseModel createBoard(
            @RequestBody BoardRequestDto boardRequestDto
    ) {
        User user = AuthUtil.getAuthenticationInfo();
        boardService.createBoard(user, boardRequestDto);
        ResponseModel responseModel = ResponseModel.builder().build();
        return responseModel;
    }

    @Operation(summary = "게시글 전체 목록", description = "게시글 전체 목록")
    @GetMapping("/list")
    public ResponseModel getAllBoard() {
        Long userId = AuthUtil.getAuthenticationInfoUserId();
        List<BoardResponseDto> board = boardService.getAllBoard(userId);
        ResponseModel responseModel = ResponseModel.builder().build();
        responseModel.addData("board", board);
        return responseModel;
    }

    @Operation(summary = "게시글 상세보기", description = "게시글 상세보기")
    @GetMapping("/list/{boardId}")
    public ResponseModel getBoard(
            @PathVariable Long boardId
    ) {
        Long userId = AuthUtil.getAuthenticationInfoUserId();
        BoardResponseDto board = boardService.getBoard(boardId, userId);
        ResponseModel responseModel = ResponseModel.builder().build();
        responseModel.addData("board", board);
        return responseModel;
    }

    @Operation(summary = "내 게시글 조회", description = "마이페이지(판매내역)")
    @GetMapping("/user/mypage")
    public ResponseModel getMyBoard() {
        Long userId = AuthUtil.getAuthenticationInfoUserId();
        List<BoardResponseDto> board = boardService.getUserBoard(userId);
        ResponseModel responseModel = ResponseModel.builder().build();
        responseModel.addData("board", board);
        return responseModel;
    }

    @Operation(summary = "유저 게시글 조회", description = "유저 프로필")
    @GetMapping("/user/{userId}")
    public ResponseModel getUserBoard(
            @PathVariable Long userId
    ) {
        List<BoardResponseDto> board = boardService.getUserBoard(userId);
        ResponseModel responseModel = ResponseModel.builder().build();
        responseModel.addData("board", board);
        return responseModel;
    }

    @Operation(summary = "게시글 수정", description = "게시글 수정<br>" +
            "반드시 수정할 값만 전달할 것<br>" +
            "BoardRequestDto<br>" +
            "String productName(not null);<br>" +
            "String productImg;<br>" +
            "LocalDate expirationDate(not null);<br>" +
            "Long price(not null);<br> +" +
            "String description;")
    @PostMapping("/update/{boardId}")
    public ResponseModel createBoard(
            @PathVariable Long boardId,
            @RequestBody BoardRequestDto boardRequestDto
    ) {
        boardService.updateBoard(boardId, boardRequestDto);
        ResponseModel responseModel = ResponseModel.builder().build();
        return responseModel;
    }

    @Operation(summary = "게시글 삭제", description = "게시글 삭제")
    @DeleteMapping("/delete/{boardId}")
    public ResponseModel deleteBoard(
            @PathVariable Long boardId
    ) {
        boardService.deleteBoard(boardId);
        ResponseModel responseModel = ResponseModel.builder().build();
        return responseModel;
    }

}
