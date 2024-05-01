package com.example.boardApi.web;

import com.example.boardApi.dto.MemberDto;
import com.example.boardApi.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberApi {
    private final MemberService memberService;

    @GetMapping("/member/list")
    public List<MemberDto> memberList() {
        return memberService.findAll();
    }
    @GetMapping("/member/{id}")
    public MemberDto memberById(@PathVariable("id") Long id) {
        return memberService.findById(id).get();
    }
    @PostMapping("/member/save")
    public HttpStatus save(@RequestBody MemberDto memberDto)  throws  Exception {
         memberService.save(memberDto);
        return HttpStatus.OK;
    }
    @PutMapping("/member/udpate")
    public HttpStatus update(@RequestBody MemberDto memberDto)  throws  Exception {
        memberService.update(memberDto);
        return HttpStatus.OK;
    }
}

