package com.example.boardApi.service;

import com.example.boardApi.domain.Authority;
import com.example.boardApi.domain.Member;
import com.example.boardApi.dto.MemberDto;
import com.example.boardApi.enums.roleEnum;
import com.example.boardApi.repository.MemberRepository;
import com.example.boardApi.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public List<MemberDto> findAll() {
        List<Member> all = memberRepository.findAll();
        List<MemberDto> resultMember = all.stream().map(o -> new MemberDto(o)).collect(Collectors.toList());
        return resultMember;
    }

    public Optional<MemberDto> findById(Long id) {
        return memberRepository.findById(id).map(o -> new MemberDto(o));
    }

    /**
     * @Program 회원 가입
     * @Param   MemberDto
     * @Return  void
     * */
    @Transactional
    public void save(MemberDto memberDto) {
        if(memberRepository.findByEmail(memberDto.getEmail()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        Authority authority = new Authority().builder()
                .authorityName("ROLE_USER")
                .build();

        Member member = new Member();
        member.setEmail(memberDto.getEmail())
                .setPassword(passwordEncoder.encode(memberDto.getPassword()))
                .setAge(memberDto.getAge())
                .setUse_yn(memberDto.getUse_yn())
                .setAuthorities(Collections.singleton(authority));

        memberRepository.save(member);
    }

    /**
     * @Program 유저,권한 정보를 가져오는 메소드
     * @Param   String email
     * @Return  Optional<Member>
     * */
    public Optional<Member> getUserWithAuthorities(String email) {
        return memberRepository.findOneWithAuthoritiesByEmail(email);
    }

    /**
     * @Program 현재 securityContext에 저장된 username의 정보만 가져오는 메소드
     * @Param
     * @Return  Optional<Member>
     * */
    public Optional<Member> getMyUserWithAuthorities(){
        return SecurityUtil.getCurrentUserEmail()
                .flatMap(member -> memberRepository.findOneWithAuthoritiesByEmail(member));
    }

    @Transactional
    public void update(MemberDto memberDto) {
        Member member = memberRepository.getById(memberDto.getId());
        member.setEmail(memberDto.getEmail());
        member.setUsername(memberDto.getUsername());
        member.setPassword(memberDto.getPassword());
        member.setUse_yn(memberDto.getUse_yn());
        member.setAge(memberDto.getAge());
    }

}
