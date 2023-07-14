package com.inhahackathon.foodmarket.type.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Embeddable
public class LikesPK implements Serializable {

    @EqualsAndHashCode.Include
    private User userId;

    @EqualsAndHashCode.Include
    private Board boardId;

}
