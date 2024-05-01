package com.example.boardApi.repository;

import com.example.boardApi.domain.QBoard;
import com.example.boardApi.domain.QMember;
import com.example.boardApi.dto.BoardDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.spel.ast.Projection;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.boardApi.domain.QBoard.*;
import static com.example.boardApi.domain.QMember.*;


@Repository
@RequiredArgsConstructor
public class BoardCustomRepositoryImpl implements  BoardCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<BoardDto> boardList(BoardDto boardSearch) {
        return queryFactory.select(Projections.constructor(BoardDto.class,
                board.board_id,
                board.subject,
                board.content,
                member)).from(board).leftJoin(board.member,member).fetch();
    }
}
