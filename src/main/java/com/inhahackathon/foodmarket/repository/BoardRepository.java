package com.inhahackathon.foodmarket.repository;

import com.inhahackathon.foodmarket.type.entity.Board;
import com.inhahackathon.foodmarket.type.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findAllByWriterId(User writerId);

}
