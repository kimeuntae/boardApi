package com.example.boardApi.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Board {

    @Id @GeneratedValue
    @Column(name="board_id")
    private Long board_id;

    private String subject;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "board")
    private List<Reply> comments = new ArrayList<>();

    public Board() {
    }

    public Board(String subject, String content, Member member, Reply reply) {
        this.subject = subject;
        this.content = content;
        this.member = member;

        if(reply != null){
            addReply(reply);
        }
    }

    //==연관관계 메서드 ==//
    public void setMember(Member member){
        this.member = member; //내것에서 변경하고
        member.getBoardList().add(this); //member에서도 변경
    }

    //==연관관계 메서드 ==//
    public void addReply(Reply reply){
        this.comments.add(reply);
        reply.setBoard(this);
    }



}
