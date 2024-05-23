package com.example.boardApi.web;

import com.example.boardApi.config.jwt.TokenProvider;
import com.example.boardApi.dto.LoginDto;
import com.example.boardApi.dto.TokenDto;
import com.example.boardApi.enums.httpEnum;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST API 사용자 인증 체크 및 token 발급 controller
 * */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    /**
     * @Program 로그인 token 발급 API용
     * @Param   LoginDto loginDto
     * @Return  ResponseEntity<TokenDto>
     */
    @PostMapping("/authenticate")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
        // authenticate 메소드가 실행이 될 때 CustomUserDetailsService class의 loadUserByUsername 메소드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // 해당 객체를 SecurityContextHolder에 저장하고
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // authentication 객체를 createToken 메소드를 통해서 JWT Token을 생성
        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders headers = new HttpHeaders();
        // response header에 jwt token에 넣어줌
        headers.add(httpEnum.AUTHORIZATION_HEADER.getValue(), "Bearer " + jwt);

        return new ResponseEntity<>(new TokenDto(jwt), headers, HttpStatus.OK);
    }


    /**
     * @Program 로그인 token 직접 발급 API용
     * @Param   LoginDto loginDto
     * @Return  ResponseEntity<TokenDto>
     */
    @PostMapping("/authenticate2")
    public ResponseEntity<TokenDto> authorize2(@Valid @RequestBody LoginDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
        // loadUserByUsername 메소드가 실행
        UserDetails member =  userDetailsService.loadUserByUsername(loginDto.getEmail());

        if (!passwordEncoder.matches(loginDto.getPassword() , member.getPassword())) {
            throw new BadCredentialsException(loginDto.getEmail() + "Invalid password");
        }

        // 권한 체크
        List<GrantedAuthority> grantedAuthorities = member.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority("ROLE_USER"))
                .collect(Collectors.toList());
        // UsernamePasswordAuthenticationToken 객체 생성
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(member.getUsername(), member.getPassword(), grantedAuthorities);
        // JWT 토큰 생성
        String jwt = tokenProvider.createToken(authToken);
        // 해당 객체를 SecurityContextHolder에 저장하고
        SecurityContextHolder.getContext().setAuthentication(authToken);

        HttpHeaders headers = new HttpHeaders();
        // response header에 jwt token에 넣어줌
        headers.add(httpEnum.AUTHORIZATION_HEADER.getValue(), "Bearer " + jwt);

        return new ResponseEntity<>(new TokenDto(jwt), headers, HttpStatus.OK);
    }

}
