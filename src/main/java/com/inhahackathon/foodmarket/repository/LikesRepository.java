package com.inhahackathon.foodmarket.repository;

import com.inhahackathon.foodmarket.type.entity.Likes;
import com.inhahackathon.foodmarket.type.entity.LikesPK;
import com.inhahackathon.foodmarket.type.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LikesRepository extends JpaRepository<Likes, LikesPK> {

    List<Likes> findAllByUserId(User userId);

    @Modifying
    @Query("DELETE FROM Likes l WHERE l.boardId.boardId = :boardId")
    void deleteByBoardId(@Param("boardId") Long boardId);

    @Modifying
    @Query("INSERT INTO Likes(userId, boardId) SELECT u, b FROM User u JOIN Board b ON u.userId = :userId AND b.boardId = :boardId")
    void saveLikes(@Param("userId") Long userId, @Param("boardId") Long boardId);



}
