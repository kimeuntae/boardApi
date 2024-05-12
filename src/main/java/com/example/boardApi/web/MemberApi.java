package com.example.boardApi.web;

import com.example.boardApi.domain.Member;
import com.example.boardApi.dto.MemberDto;
import com.example.boardApi.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
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
    @PutMapping("/member/udpate")
    public HttpStatus update(@RequestBody MemberDto memberDto) throws Exception {
        memberService.update(memberDto);
        return HttpStatus.OK;
    }

    /**
     * @Program 회원가입
     * @Param   MemberDto memberDto
     * @Return  HttpStatus
     */
    @PostMapping("/member/save")
    public HttpStatus save(@RequestBody MemberDto memberDto) throws Exception {
        memberService.save(memberDto);
        return HttpStatus.OK;
    }
    /**
     * @Program 회원조회 By Email
     * @Param   String email
     * @Return  ResponseEntity<Member>
     */
    @GetMapping("/member/info/{email}")
    @PreAuthorize("hasAnyRole('ADMIN','ROLE_USER')")
    public ResponseEntity<Member> getUserInfo(@PathVariable(name = "email") String email) {
        return ResponseEntity.ok(memberService.getUserWithAuthorities(email).get());
    }
    /**
     * @Program Security Context 저장된 회원 조회
     * @Param
     * @Return  ResponseEntity<Member>
     */
    @GetMapping("/member/info2")
    @PreAuthorize("hasAnyRole('ADMIN','ROLE_USER')")
    public ResponseEntity<Member> getUserInfo2() {
        return ResponseEntity.ok(memberService.getMyUserWithAuthorities().get());
    }

}

