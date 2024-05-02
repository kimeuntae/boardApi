package com.example.boardApi.service;

import com.example.boardApi.domain.Member;
import com.example.boardApi.dto.MemberDto;
import com.example.boardApi.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final initService initService;

    @PostConstruct
    public void init() {
        initService.dbinit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class initService {

        private final MemberRepository memberRepository;

        public void dbinit1() {
            for (int i = 0; i < 100; i++) {
                Member member = new Member();
                member.setEmail("email@email"+i+".com");
                member.setPassword("password");
                member.setAge(5);
                member.setUse_yn("1");
                memberRepository.save(member);
            }

        }

    }


}
