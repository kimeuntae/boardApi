package com.example.boardApi.service;

import com.example.boardApi.domain.Board;
import com.example.boardApi.domain.Member;
import com.example.boardApi.dto.BoardDto;
import com.example.boardApi.dto.MemberDto;
import com.example.boardApi.repository.BoardRepository;
import com.example.boardApi.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public List<BoardDto> boardList(BoardDto boardDto){
        List<BoardDto> boardDtos = boardRepository.boardList(boardDto);
        return boardDtos;
    }

    @Transactional
    public void save(BoardDto boardDto){
        Member findMember = memberRepository.findById(boardDto.getMember().getId()).get();
        Board board = new Board(
                    boardDto.getSubject(),
                    boardDto.getContent(),
                    findMember,
                null
        );
        boardRepository.save(board);
    }

}
