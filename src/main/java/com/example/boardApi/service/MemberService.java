package com.example.boardApi.service;

import com.example.boardApi.domain.Member;
import com.example.boardApi.dto.MemberDto;
import com.example.boardApi.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public List<MemberDto> findAll() {
        List<Member> all = memberRepository.findAll();
        List<MemberDto> resultMember = all.stream().map(o->new MemberDto(o)).collect(Collectors.toList());
        return resultMember;
    }
    public Optional<MemberDto> findById(Long id) {
        return memberRepository.findById(id).map(o->new MemberDto(o));
    }

    @Transactional
    public void save(MemberDto memberDto) {
        Member member = new Member(memberDto);
        memberRepository.save(member);
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
