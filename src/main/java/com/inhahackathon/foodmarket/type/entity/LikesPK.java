package com.inhahackathon.foodmarket.type.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Embeddable
public class LikesPK implements Serializable {

    @EqualsAndHashCode.Include
    private User userId;

    @EqualsAndHashCode.Include
    private Board boardId;

}
