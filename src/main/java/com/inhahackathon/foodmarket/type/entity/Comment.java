package com.inhahackathon.foodmarket.type.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comment", indexes = {
        @Index(name = "idx_writer_id", columnList = "writer_id"),
        @Index(name = "idx_board_id", columnList = "board_id")
})
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serialNum;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "writer_id")
    private User writerId;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board boardId;

}
