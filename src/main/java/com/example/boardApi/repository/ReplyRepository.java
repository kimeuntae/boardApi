package com.example.boardApi.repository;


import com.example.boardApi.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  ReplyRepository  extends JpaRepository<Reply, Long> {

}
