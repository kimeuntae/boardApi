package com.example.boardApi.web;

import com.example.boardApi.dto.LoginDto;
import com.example.boardApi.dto.TokenDto;
import com.example.boardApi.service.MemberPrincipalDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 화면 사용자 인증 체크 및 token 발급 controller
 * */
@Slf4j
@Controller
@RequestMapping("/form")
@RequiredArgsConstructor
public class FormAuthController {

    /**
     * CustomAuthenticationProvider 통해 인증
     * */
    @GetMapping("/login_authenticate2")
    public String login_authenticate(@AuthenticationPrincipal MemberPrincipalDetails memberPrincipalDetails) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MemberPrincipalDetails userDetails = (MemberPrincipalDetails) principal;
        log.debug("login_authenticate");
        return null;
    }

    @GetMapping("/login_authenticate")
    public ResponseEntity<TokenDto> test(@AuthenticationPrincipal MemberPrincipalDetails memberPrincipalDetails) {
        log.debug("TEST");
        return null;
    }
}
