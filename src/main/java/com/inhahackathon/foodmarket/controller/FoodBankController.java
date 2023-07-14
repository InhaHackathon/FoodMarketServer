package com.inhahackathon.foodmarket.controller;

import com.inhahackathon.foodmarket.service.FoodBankService;
import com.inhahackathon.foodmarket.type.dto.FoodBankRequestDto;
import com.inhahackathon.foodmarket.type.dto.ResponseModel;
import com.inhahackathon.foodmarket.type.dto.FoodBankResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/foodbank")
@RequiredArgsConstructor
@Tag(name = "FoodBank")
@Slf4j
public class FoodBankController {

    private final FoodBankService foodBankService;

    @Operation(summary = "푸드뱅크 전체 목록", description = "푸드뱅크 전체 목록<br>" +
            "현재 경위도 입력(ex: 37.45085008, 126.6543226)<br>" +
            "Double latitude;<br>" +
            "Double longitude;")
    @PostMapping("/list")
    public ResponseModel getAllFoodBank (
            @RequestBody FoodBankRequestDto foodBankRequestDto
    ) {
        List<FoodBankResponseDto> foodBankResponseDtoList = foodBankService.getAllFoodbank(foodBankRequestDto);
        ResponseModel responseModel = ResponseModel.builder().build();
        responseModel.addData("foodbank", foodBankResponseDtoList);
        return responseModel;
    }

}
