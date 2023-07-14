package com.inhahackathon.foodmarket.service;

import com.google.common.base.Strings;
import com.inhahackathon.foodmarket.exception.NotFoundException;
import com.inhahackathon.foodmarket.repository.BoardRepository;
import com.inhahackathon.foodmarket.repository.LikesRepository;
import com.inhahackathon.foodmarket.repository.UserRepository;
import com.inhahackathon.foodmarket.type.dto.BoardRequestDto;
import com.inhahackathon.foodmarket.type.dto.BoardResponseDto;
import com.inhahackathon.foodmarket.type.entity.Board;
import com.inhahackathon.foodmarket.type.entity.Likes;
import com.inhahackathon.foodmarket.type.entity.LikesPK;
import com.inhahackathon.foodmarket.type.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;
    private final LikesRepository likesRepository;
    private final UserRepository userRepository;

    public void createBoard(User writerId, BoardRequestDto boardRequestDto) {
        Board board = Board.builder()
                .writerId(writerId)
                .productName(boardRequestDto.getProductName())
                .productImg(boardRequestDto.getProductImg())
                .expirationDate(boardRequestDto.getExpirationDate())
                .price(boardRequestDto.getPrice())
                .description(boardRequestDto.getDescription())
                .likeCount(0L)
                .build();
        boardRepository.save(board);
    }

    public void updateBoard(Long boardId, BoardRequestDto boardRequestDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NotFoundException("해당 게시글이 없습니다."));
        if (!Strings.isNullOrEmpty(boardRequestDto.getProductName())) {
            board.setProductName(boardRequestDto.getProductName());
        }
        if (!Strings.isNullOrEmpty(boardRequestDto.getProductImg())) {
            board.setProductImg(boardRequestDto.getProductImg());
        }
        if (!Strings.isNullOrEmpty(boardRequestDto.getExpirationDate().toString())) {
            board.setExpirationDate(boardRequestDto.getExpirationDate());
        }
        if (!Strings.isNullOrEmpty(String.valueOf(boardRequestDto.getPrice()))) {
            board.setPrice(boardRequestDto.getPrice());
        }
        if (!Strings.isNullOrEmpty(boardRequestDto.getDescription())) {
            board.setDescription(boardRequestDto.getDescription());
        }
        boardRepository.save(board);
    }

    @Transactional
    public void deleteBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NotFoundException("해당 게시글이 없습니다."));
        likesRepository.deleteByBoardId(boardId);
        boardRepository.delete(board);
    }

    @Transactional
    public void deleteAllUserBoard(User writerId) {
        List<Board> boardList = boardRepository.findAllByWriterId(writerId);
        log.info("boardList : {}", boardList);
        for (Board b : boardList) {
            log.info("board({}) : {}", b.getBoardId(), b);
            likesRepository.deleteByBoardId(b.getBoardId());
            boardRepository.delete(b);
            log.info("Deleted board: {}", b);
        }
    }

    @Transactional
    public BoardResponseDto getBoard(Long boardId, Long userId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NotFoundException("해당 게시글이 없습니다."));
        User user = userRepository.getUserByUserId(userId).orElseThrow(() -> new NotFoundException("해당 유저가 없습니다."));
        Boolean isLike = (likesRepository.findById(new LikesPK(user, board)).isEmpty()) ? false : true;
        BoardResponseDto boardResponseDto = BoardResponseDto.builder()
                .boardId(boardId)
                .productName(board.getProductName())
                .productImg(board.getProductImg())
                .expirationDate(board.getExpirationDate())
                .price(board.getPrice())
                .description(board.getDescription())
                .likeCount(board.getLikeCount())
                .isLike(isLike)
                .build();
        return boardResponseDto;
    }

    @Transactional
    public List<BoardResponseDto> getAllBoard(Long userId) {
        List<BoardResponseDto> boardResponseDtoList = new ArrayList<>();
        List<Board> boardList = boardRepository.findAll();
        User user = userRepository.getUserByUserId(userId).orElseThrow(() -> new NotFoundException("해당 유저가 없습니다."));
        for (Board b : boardList) {
            Boolean isLike = (likesRepository.findById(new LikesPK(user, b)).isEmpty()) ? false : true;
            BoardResponseDto boardResponseDto = BoardResponseDto.builder()
                    .boardId(b.getBoardId())
                    .productName(b.getProductName())
                    .productImg(b.getProductImg())
                    .expirationDate(b.getExpirationDate())
                    .price(b.getPrice())
                    .description(b.getDescription())
                    .likeCount(b.getLikeCount())
                    .isLike(isLike)
                    .build();
            boardResponseDtoList.add(boardResponseDto);
        }
        return boardResponseDtoList;
    }

    @Transactional
    public List<BoardResponseDto> getUserBoard(Long userId) {
        User user = userRepository.getUserByUserId(userId).orElseThrow(() -> new NotFoundException("해당 유저가 없습니다."));
        List<BoardResponseDto> boardResponseDtoList = new ArrayList<>();
        List<Board> boardList = boardRepository.findAllByWriterId(user);
        for (Board b : boardList) {
            Boolean isLike = (likesRepository.findById(new LikesPK(user, b)).isEmpty()) ? false : true;
            BoardResponseDto boardResponseDto = BoardResponseDto.builder()
                    .boardId(b.getBoardId())
                    .productName(b.getProductName())
                    .productImg(b.getProductImg())
                    .expirationDate(b.getExpirationDate())
                    .price(b.getPrice())
                    .description(b.getDescription())
                    .likeCount(b.getLikeCount())
                    .isLike(isLike)
                    .build();
            boardResponseDtoList.add(boardResponseDto);
        }
        return boardResponseDtoList;
    }

}
