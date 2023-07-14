package com.inhahackathon.foodmarket.repository;

import com.inhahackathon.foodmarket.type.entity.FoodBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodBankRepository extends JpaRepository<FoodBank, Long> {
    List<FoodBank> findAll();
}