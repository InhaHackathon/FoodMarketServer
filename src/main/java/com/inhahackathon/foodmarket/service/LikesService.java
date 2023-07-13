package com.inhahackathon.foodmarket.service;

import com.inhahackathon.foodmarket.repository.LikesRepository;
import com.inhahackathon.foodmarket.type.entity.Likes;
import com.inhahackathon.foodmarket.type.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikesService {

    @Autowired
    LikesRepository likesRepository;

    public void deleteLikesByUserId(User userId) {
        List<Likes> likesList = likesRepository.findAllByUserId(userId);
        likesRepository.deleteInBatch(likesList);
    }

}
