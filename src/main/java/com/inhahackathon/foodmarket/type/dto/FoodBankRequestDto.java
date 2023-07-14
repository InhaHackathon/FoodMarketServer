package com.inhahackathon.foodmarket.type.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class FoodBankRequestDto {

    Double latitude;

    Double longitude;

}
