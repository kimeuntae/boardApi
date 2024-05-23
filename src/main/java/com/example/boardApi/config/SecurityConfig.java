package com.example.boardApi.config;

import com.example.boardApi.config.jwt.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    // in memory 방식으로 인증 처리를 진행 하기 위해 기존엔 Override 하여 구현했지만
    // Spring Security 5.7.0 버전부터는 AuthenticationManagerBuilder를 직접 생성하여
    // AuthenticationManager를 생성해야 한다.
    @Autowired
    public void configure (AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider);
    }

    /** PasswordEncoder는 BCryptPasswordEncoder를 사용 */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /** REST API 인증 방식  */
    @Bean
    @Order(0)
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .securityMatcher("/api/**","/board/**")
                /** token을 사용하는 방식이기 때문에 csrf를 disable합니다. */
                .cors(withDefaults())
                //.csrf((c) -> c.disable())
                .csrf((c) -> c.ignoringRequestMatchers("/api/**","/board/**","/login")) //csrf 해당 URL 비활성화한다.
                .headers((h) -> h.frameOptions(frame -> frame.disable()))
                /** 세션을 사용하지 않기 때문에 STATELESS로 설정 */
                .sessionManagement((session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)))
                .httpBasic(withDefaults())
                /** 5.7.0 이상 부터는  WebSecurityConfigurerAdapter deprecated 되어 아래와같이 사용 */
                .authenticationProvider(customAuthenticationProvider)
                .exceptionHandling((except) -> except
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                )
                /** "api/**" 이하의 URL에서만 시큐리티 작동하도록 */
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                /** HttpServletRequest를 사용하는 요청들에 대한 접근제한을 설정하겠다. */
                                .requestMatchers("/api/authenticate").permitAll() // 로그인 api
                                .requestMatchers("/api/authenticate2").permitAll() // 로그인 api
                                .requestMatchers("/api/login_authenticate").permitAll() // 로그인 api
                                .requestMatchers("/api/login_authenticate2").permitAll() // 로그인 api
                                .requestMatchers("/api/member/save").permitAll() // 회원가입 api
                                .requestMatchers("/favicon.ico").permitAll()
                                .requestMatchers(PathRequest.toH2Console()).permitAll()// h2-console, favicon.ico 요청 인증 무시
                                /** .anyRequest().authenticated() // 그 외 인증 없이 접근X */
                                .requestMatchers("/board/**").hasAuthority("ROLE_USER")
                                .anyRequest().authenticated() // 그 외 인증 없이 접근X
                )
                .addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    /**
     * 폼화면 인증 방식
     */
    @Bean
    @Order(1)
    public SecurityFilterChain filterChain2(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                //.securityMatcher("/form/**")
                .formLogin((form) -> form
                        .loginProcessingUrl("/form/login_authenticate").permitAll()
                        .defaultSuccessUrl("/form/login_authenticate2", true).permitAll()
                 )
                .authorizeHttpRequests((e) -> e
                        .requestMatchers("/form/login_authenticate").permitAll()
                        .requestMatchers("/", "/loginForm**", "/signup**", "/users").permitAll()
                        .anyRequest().authenticated())
                .csrf((c) -> c.disable());
        return httpSecurity.build();
    }

}
