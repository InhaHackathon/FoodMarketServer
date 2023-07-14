package com.inhahackathon.foodmarket.service;

import com.inhahackathon.foodmarket.repository.FoodBankRepository;
import com.inhahackathon.foodmarket.type.dto.FoodBankRequestDto;
import com.inhahackathon.foodmarket.type.entity.FoodBank;
import com.inhahackathon.foodmarket.type.dto.FoodBankResponseDto;
import com.inhahackathon.foodmarket.util.MapUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodBankService {

    private final FoodBankRepository foodBankRepository;
    private final MapUtil mapUtil;

    public List<FoodBankResponseDto> getAllFoodbank(FoodBankRequestDto foodBankRequestDto) {
        List<FoodBank> foodBankList = foodBankRepository.findAll();
        List<FoodBankResponseDto> foodBankResponseDtoList = new ArrayList<>();
        double latitude = foodBankRequestDto.getLatitude();
        double longitude = foodBankRequestDto.getLongitude();
        for (FoodBank f : foodBankList) {
            double directDistance = mapUtil.calculateDistance(f, latitude, longitude);
            if (directDistance > 10000)
                continue;
            FoodBankResponseDto foodBankResponseDto = FoodBankResponseDto.builder()
                    .foodBankId(f.getFoodBankId())
                    .district(f.getDistrict())
                    .centerType(f.getCenterType())
                    .name(f.getName())
                    .tel(f.getTel())
                    .address(f.getAddress())
                    .detailAddress(f.getDetailAddress())
                    .latitude(f.getLatitude())
                    .longitude(f.getLongitude())
                    .directDistance(directDistance)
                    .build();
            foodBankResponseDtoList.add(foodBankResponseDto);
        }
        Collections.sort(foodBankResponseDtoList, (o1, o2) -> (int)(o1.getDirectDistance() - o2.getDirectDistance()));
        if (foodBankResponseDtoList.size() > 10) {
            foodBankResponseDtoList = foodBankResponseDtoList.subList(0, 10);
        }
        return foodBankResponseDtoList;
    }

}