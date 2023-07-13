package com.inhahackathon.foodmarket.repository;

import com.inhahackathon.foodmarket.type.entity.Likes;
import com.inhahackathon.foodmarket.type.entity.LikesPK;
import com.inhahackathon.foodmarket.type.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikesRepository extends JpaRepository<Likes, LikesPK> {

    List<Likes> findAllByUserId(User userId);

}
