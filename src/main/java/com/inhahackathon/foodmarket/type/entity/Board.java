package com.inhahackathon.foodmarket.type.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "board", indexes =
    @Index(name = "idx_writer_id", columnList = "writer_id")
)
public class Board {

    @Id
    private Long boardId;

    @ManyToOne
    @JoinColumn(name = "writer_id")
    private User writerId;

    @Column(nullable = false)
    private String productName;

    private String productImg;

    @Column(nullable = false)
    private LocalDate expirationDate;

    @Column(nullable = false)
    private Long price;

    @Size(max = 500)
    private String description;

    private Long likeCount;

}
