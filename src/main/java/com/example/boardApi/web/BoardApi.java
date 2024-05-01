package com.example.boardApi.web;

import com.example.boardApi.domain.Board;
import com.example.boardApi.dto.BoardDto;
import com.example.boardApi.dto.MemberDto;
import com.example.boardApi.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardApi {

    private final BoardService boardService;

    @GetMapping("/board/list")
    public List<BoardDto> boardList(@RequestBody BoardDto boardDto) {
        return boardService.boardList(boardDto);
    }
    @PostMapping("/board/save")
    public HttpStatus save(@RequestBody BoardDto boardDto)  throws  Exception {
        boardService.save(boardDto);
        return HttpStatus.OK;
    }
}
