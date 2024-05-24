package com.example.boardApi.config.jwt;

import com.example.boardApi.enums.httpEnum;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.security.Security;

/**
 * 실제로 이 컴포넌트를 이용하는 것은 인증 작업을 진행하는 Filter
 * 이 필터는 검증이 끝난 JWT로부터 유저정보를 받아와서 UsernamePasswordAuthenticationFilter 로 전달
 * GenericFilterBean 는 한번만 호출됌 
 */
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends GenericFilterBean {
    
    private final TokenProvider tokenProvider;

    /**
     * 실제 필터릴 로직
     * 토큰의 인증 정보를 SecurityContext에 저장하는 역할 수행
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String jwt = resolveToken(request);
        String requestURI = request.getRequestURI();

        /**  유요한 토큰 체크 */ /** null인경우 인증으로 */
        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            Authentication authentication = tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
        } else {
            log.info("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
        }
        log.info("JwtFilter [doFilter]");
        filterChain.doFilter(servletRequest, servletResponse);

    }

    /**
     * Request Header 에서 토큰 정보를 꺼내오기 위한 메소드
     */
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(httpEnum.AUTHORIZATION_HEADER.getValue());

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}
