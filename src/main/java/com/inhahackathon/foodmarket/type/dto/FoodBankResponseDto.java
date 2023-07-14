package com.inhahackathon.foodmarket.type.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoodBankResponseDto {

    @Id
    private Long foodBankId;

    private String district;

    private String centerType;

    private String name;

    private String tel;

    private String address;

    private String detailAddress;

    private Double latitude;

    private Double longitude;

    private Double directDistance;

}
