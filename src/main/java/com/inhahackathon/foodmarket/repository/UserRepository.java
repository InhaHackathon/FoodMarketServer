package com.inhahackathon.foodmarket.repository;

import com.inhahackathon.foodmarket.type.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User getUserByUid(String uid);

    Optional<User> getUserByUserId(Long userId);

}
