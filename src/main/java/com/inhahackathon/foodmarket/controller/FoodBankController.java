package com.inhahackathon.foodmarket.controller;

import com.inhahackathon.foodmarket.service.FindNearbyFoodBankService;
import com.inhahackathon.foodmarket.type.entity.FoodBankDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class FoodBankController {

    @RequestMapping("/foodBank")
    public List<FoodBankDto> foodBankDto(){
        return FindNearbyFoodBankService.calAllDistance(37.3450775,126.654308);
    }
}
