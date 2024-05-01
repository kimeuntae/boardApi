package com.example.boardApi.repository;


import com.example.boardApi.dto.BoardDto;

import java.util.List;

public interface BoardCustomRepository {
    List<BoardDto> boardList(BoardDto boardSearch);
}
