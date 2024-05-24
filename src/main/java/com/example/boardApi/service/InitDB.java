package com.example.boardApi.service;

import com.example.boardApi.domain.Authority;
import com.example.boardApi.domain.Member;
import com.example.boardApi.dto.MemberDto;
import com.example.boardApi.enums.roleEnum;
import com.example.boardApi.repository.AuthorityRepository;
import com.example.boardApi.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

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
        private final AuthorityRepository authorityRepository;
        private final PasswordEncoder passwordEncoder;

        public void dbinit1() {

            Authority authority = new Authority().builder()
                    .authorityName("ROLE_USER")
                    .build();

            authorityRepository.save(authority);

            Member member = new Member();
            member.setEmail("etkim87@gmail.com")
                    .setPassword(passwordEncoder.encode("admin"))
                    .setAge(10)
                    .setUse_yn("1")
                    .setAuthorities(Collections.singleton(authority));
            memberRepository.save(member);

            /*for (int i = 0; i < 100; i++) {
                Member member = new Member();
                member.setEmail("email@email"+i+".com");
                member.setPassword("password");
                member.setAge(5);
                member.setUse_yn("1");
                memberRepository.save(member);
            }*/

        }

    }


}
