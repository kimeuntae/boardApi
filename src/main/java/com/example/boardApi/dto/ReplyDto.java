package com.example.boardApi.dto;

import com.example.boardApi.domain.Board;
import com.example.boardApi.domain.Member;
import com.example.boardApi.domain.Reply;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class ReplyDto {
    private Long reply_id;
    private String content;
    private BoardDto board;
    private MemberDto member;

    public ReplyDto() {
    }
    public ReplyDto(Reply reply) {
        this.reply_id = reply.getReply_id();
        this.content = reply.getContent();
        this.board = new BoardDto(reply.getBoard());
        this.member = new MemberDto(reply.getMember());
    }
}
