package com.example.boardApi.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

//https://curiousjinan.tistory.com/entry/spring-boot-security-auth-provider-handler-5
//@RequiredArgsConstructor
//@Component("authenticationProvider") //AuthenticationProvider 순환참조로 삭제
/** 로그인 페이지 호출시 AuthenticationProvider 에서 체크
 *  loginProcessingUrl("/api/login_authenticate") 설정
 * */
@Slf4j
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

//    @Autowired
//    public CustomAuthenticationProvider(UserDetailsService userService,PasswordEncoder passwordEncoder) {
//        this.userDetailsService = userService;
//        this.passwordEncoder = passwordEncoder;
//    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.debug("2.CustomAuthenticationProvider");

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

        // AuthenticationFilter에서 생성된 토큰으로부터 ID, PW를 조회
        String loginId = token.getName();
        String userPassword = (String) token.getCredentials();

        // Spring security - UserDetailsService를 통해 DB에서 username으로 사용자 조회
        UserDetails member =  userDetailsService.loadUserByUsername(loginId);

        //member.getAuthorities()
        //Member member = memberPrincipalDetails.getMember();
        // 대소문자를 구분하는 matches() 메서드로 db와 사용자가 제출한 비밀번호를 비교
        if (!passwordEncoder.matches(userPassword,member.getPassword())) {
            throw new BadCredentialsException(loginId + "Invalid password");
        }

        List<GrantedAuthority> grantedAuthorities = member.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority("ROLE_USER"))
                .collect(Collectors.toList());

        // 인증이 성공하면 인증된 사용자의 정보와 권한을 담은 새로운 UsernamePasswordAuthenticationToken을 반환한다.
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(member.getUsername(), userPassword, grantedAuthorities);
        //return authToken;
        return new CustomAuthenticationToken(member.getUsername(), member.getPassword(), member.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        /** AuthenticationProvider에서 해당 부분 먼저 호출 */
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
        //return authentication.isAssignableFrom(CustomAuthenticationToken.class);
    }
}
