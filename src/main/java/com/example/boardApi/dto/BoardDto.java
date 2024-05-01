package com.example.boardApi.dto;

import com.example.boardApi.domain.Board;
import com.example.boardApi.domain.Member;
import com.example.boardApi.domain.Reply;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BoardDto {

    private Long board_id;
    private String subject;
    private String content;
    private MemberDto member;
    private List<ReplyDto> comments = new ArrayList<>();

    public BoardDto() {
    }
    public BoardDto(Board  board) {
        this.board_id = board.getBoard_id();
        this.subject = board.getSubject();
        this.content = board.getContent();
        this.member = new MemberDto(board.getMember());
    }
    public BoardDto(Long board_id, String subject, String content, Member member) {
        this.board_id = board_id;
        this.subject = subject;
        this.content = content;
        this.member = new MemberDto(member) ;
    }
}
