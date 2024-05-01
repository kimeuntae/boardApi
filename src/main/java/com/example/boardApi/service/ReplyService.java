package com.example.boardApi.service;

import com.example.boardApi.domain.Board;
import com.example.boardApi.domain.Member;
import com.example.boardApi.domain.Reply;
import com.example.boardApi.dto.MemberDto;
import com.example.boardApi.dto.ReplyDto;
import com.example.boardApi.repository.BoardRepository;
import com.example.boardApi.repository.MemberRepository;
import com.example.boardApi.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Reply save(ReplyDto replyDto) {

        Member member = memberRepository.findById(replyDto.getMember().getId()).get();
        Board board = boardRepository.findById(replyDto.getBoard().getBoard_id()).get();

        Reply reply = new Reply(replyDto.getContent(),board,member);
        return replyRepository.save(reply);
    }

    public List<ReplyDto> findAll() {
        List<Reply> replies = replyRepository.findAll();
        List<ReplyDto> repliesDto = replies.stream().map(o-> new ReplyDto(o)).collect(Collectors.toList());
        return repliesDto;
    }
}
