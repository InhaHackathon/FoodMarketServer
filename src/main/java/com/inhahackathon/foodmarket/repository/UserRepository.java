package com.inhahackathon.foodmarket.repository;

import com.inhahackathon.foodmarket.type.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    User getUserByUid(String uid);

}
