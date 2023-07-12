package com.inhahackathon.foodmarket.type.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class LikesPK implements Serializable {

    private User userId;

    private Board boardId;

}
