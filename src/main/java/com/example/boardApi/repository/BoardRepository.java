package com.example.boardApi.repository;

import com.example.boardApi.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  BoardRepository extends JpaRepository<Board, Long> ,BoardCustomRepository {

}
