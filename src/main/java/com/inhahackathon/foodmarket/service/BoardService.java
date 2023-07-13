package com.inhahackathon.foodmarket.service;

import com.inhahackathon.foodmarket.repository.BoardRepository;
import com.inhahackathon.foodmarket.repository.LikesRepository;
import com.inhahackathon.foodmarket.type.entity.Board;
import com.inhahackathon.foodmarket.type.entity.Likes;
import com.inhahackathon.foodmarket.type.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;
    private final LikesRepository likesRepository;

    @Transactional
    public void deleteAllBoard(User writerId) {
        log.info("Deleting boards and related likes for writerId: {}", writerId);
        List<Board> boardList = boardRepository.findAllByWriterId(writerId);
        for (Board board : boardList) {
            List<Likes> likesList = likesRepository.findAllByBoardId(board);
            for (Likes like : likesList) {
                likesRepository.delete(like);
            }
            boardRepository.delete(board);
        }
    }

}
