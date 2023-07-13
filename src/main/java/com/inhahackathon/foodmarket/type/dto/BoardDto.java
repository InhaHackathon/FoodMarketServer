package com.inhahackathon.foodmarket.type.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class BoardDto {

    private Long boardId;

    private String productName;

    private String productImg;

    private LocalDate expirationDate;

    private Long price;

    private String description;

    private Long likeCount;

    private Boolean isLike;

}
