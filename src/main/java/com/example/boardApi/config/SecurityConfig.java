package com.example.boardApi.config;

import com.example.boardApi.config.jwt.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Jwt 및 시큐리티 기능 설정
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    /** PasswordEncoder는 BCryptPasswordEncoder를 사용 */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                /** token을 사용하는 방식이기 때문에 csrf를 disable합니다. */
                .cors(withDefaults())
                .csrf((c) -> c.disable())
                .headers((h) -> h.frameOptions(frame -> frame.disable()))
                /** 세션을 사용하지 않기 때문에 STATELESS로 설정 */
                .sessionManagement((session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)))
                .httpBasic(withDefaults())
                .exceptionHandling((except) -> except
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                )
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                /** HttpServletRequest를 사용하는 요청들에 대한 접근제한을 설정하겠다. */
                                .requestMatchers("/api/authenticate").permitAll() // 로그인 api
                                .requestMatchers("/api/member/save").permitAll() // 회원가입 api
                                .requestMatchers("/favicon.ico").permitAll()
                                .requestMatchers(PathRequest.toH2Console()).permitAll()// h2-console, favicon.ico 요청 인증 무시
                                /** .anyRequest().authenticated() // 그 외 인증 없이 접근X */
                                .requestMatchers("/board/**").hasAuthority("ROLE_USER")
                                .anyRequest().authenticated() // 그 외 인증 없이 접근X
                )
                .addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);
        // JwtFilter를 addFilterBefore로 등록했던 JwtSecurityConfig class 적용
        //.with(new JwtSecurityConfig(tokenProvider), Customizer.withDefaults());
        return httpSecurity.build();
    }

}
