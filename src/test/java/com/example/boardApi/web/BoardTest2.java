package com.example.boardApi.web;

import com.example.boardApi.domain.Board;
import com.example.boardApi.domain.Member;
import com.example.boardApi.domain.Reply;
import com.example.boardApi.repository.BoardRepository;
import com.example.boardApi.repository.MemberRepository;
import com.example.boardApi.repository.ReplyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class BoardTest2 {
	//
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    ReplyRepository replyRepository;

    @Test
    public void test() {
        /*Member findMember = memberRepository.findByEmail("etkim87@daum.net4").get();
        Board findBoard = boardRepository.getById(2L);
        Reply reply = new Reply();
        reply.setContent("test11");
        //
        replyRepository.save(reply);
        findBoard.addReply(reply);
        findBoard.setMember(findMember);
        boardRepository.save(findBoard);*/

    }
}
