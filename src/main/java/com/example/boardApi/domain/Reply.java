package com.example.boardApi.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Reply {

    @Id @GeneratedValue
    @Column(name = "reply_id")
    private Long reply_id;

    private String content;

    public Reply() {
    }

    public Reply(String content, Board board, Member member) {
        this.content = content;
        this.board = board;
        this.member = member;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

}
