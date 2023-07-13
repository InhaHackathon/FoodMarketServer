package com.inhahackathon.foodmarket.type.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Embeddable
public class LikesPK implements Serializable {

    @EqualsAndHashCode.Include
    private User userId;

    @EqualsAndHashCode.Include
    private Board boardId;

}
