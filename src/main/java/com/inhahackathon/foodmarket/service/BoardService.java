package com.inhahackathon.foodmarket.service;

import com.inhahackathon.foodmarket.repository.BoardRepository;
import com.inhahackathon.foodmarket.type.entity.Board;
import com.inhahackathon.foodmarket.type.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    @Autowired
    BoardRepository boardRepository;

    public void deleteBoardByWriterId(User writerId) {
        List<Board> boardList = boardRepository.findAllByWriterId(writerId);
        boardRepository.deleteInBatch(boardList);
    }

}
