package com.inhahackathon.foodmarket.service;

import com.inhahackathon.foodmarket.repository.FoodBankRepository;
import com.inhahackathon.foodmarket.type.entity.FoodBank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodBankService {
    private static FoodBankRepository foodBankRepository;

    @Autowired
    public FoodBankService(FoodBankRepository foodBankRepository) {
        this.foodBankRepository = foodBankRepository;
    }

    public static List<FoodBank> getAll() {
        return foodBankRepository.findAll();
    }
}