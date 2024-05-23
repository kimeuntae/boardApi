package com.example.boardApi.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomAuthenticationToken extends AbstractAuthenticationToken {
    private String email;
    private String credentials;

    /**
     * 인증 받기전
     */
    public CustomAuthenticationToken(String email, String credentials) {
        //super(authorities);
        super(null);
        this.email = email;
        this.credentials = credentials;
        setAuthenticated(false);
    }

    /**
     * 인증 성공 이후 결과
     * 리턴시 defaultSuccessUrl 설정한 컨트롤러로 호출된다.
     */
    public CustomAuthenticationToken(String email, String credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.email = email;
        this.credentials = credentials;
        setAuthenticated(true); //해당값TRUE지 인증통과
    }

    public CustomAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.email;
    }
}
