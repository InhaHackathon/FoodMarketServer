package com.inhahackathon.foodmarket.service;

import com.inhahackathon.foodmarket.exception.NotFoundException;
import com.inhahackathon.foodmarket.repository.BoardRepository;
import com.inhahackathon.foodmarket.repository.LikesRepository;
import com.inhahackathon.foodmarket.repository.UserRepository;
import com.inhahackathon.foodmarket.type.dto.BoardResponseDto;
import com.inhahackathon.foodmarket.type.entity.Board;
import com.inhahackathon.foodmarket.type.entity.Likes;
import com.inhahackathon.foodmarket.type.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikesService {

    private final LikesRepository likesRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public void createLikeBoard(Long userId, Long boardId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("해당 사용자가 없습니다."));
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NotFoundException("해당 게시글이 없습니다."));
        likesRepository.saveLikes(user.getUserId(), board.getBoardId());
        Long likeCount = likesRepository.countByBoardId(board);
        board.setLikeCount(likeCount);
        boardRepository.save(board);
    }

    @Transactional
    public void deleteLikeBoard(Long userId, Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NotFoundException("해당 게시글이 없습니다."));
        likesRepository.deleteByUserIdAndBoardId(userId, boardId);
        Long likeCount = likesRepository.countByBoardId(board);
        board.setLikeCount(likeCount);
        boardRepository.save(board);
    }

    @Transactional
    public List<BoardResponseDto> getLikeBoard(Long userId) {
        User user = userRepository.getUserByUserId(userId).orElseThrow(() -> new NotFoundException("해당 유저가 없습니다."));
        List<Likes> likesList = likesRepository.findAllByUserId(user);
        List<BoardResponseDto> boardList = new ArrayList<>();
        for (Likes l : likesList) {
            Board board = boardRepository.findById(l.getBoardId().getBoardId()).orElseThrow(() -> new NotFoundException("해당 게시글이 없습니다."));
            BoardResponseDto boardResponseDto = BoardResponseDto.builder()
                    .boardId(board.getBoardId())
                    .productName(board.getProductName())
                    .productImg(board.getProductImg())
                    .expirationDate(board.getExpirationDate())
                    .price(board.getPrice())
                    .description(board.getDescription())
                    .likeCount(board.getLikeCount())
                    .isLike(true)
                    .build();
            boardList.add(boardResponseDto);
        }
        return boardList;
    }

}
