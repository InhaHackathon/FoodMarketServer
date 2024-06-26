package com.inhahackathon.foodmarket.type.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@IdClass(LikesPK.class)
@Entity
public class Likes {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @Id
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board boardId;

    private Likes(User userId, Board boardId) {
        this.userId = userId;
        this.boardId = boardId;
    }

}