package com.example.boardApi.service;

import com.example.boardApi.domain.Member;
import com.example.boardApi.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * authenticationManagerBuilder.getObject().authenticate(authenticationToken); 실행 시 loadUserByUsername 실행
 * */
@Component("userDetailService")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomUserDetailsService  implements UserDetailsService {
    private final MemberRepository memberRepository;

    /**
     * @Program 유저 조회
     * @Param   email
     * @Return  UserDetails
     * */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return memberRepository.findByEmail(email)
                .map(member -> getUser(email, member)) //해당부분에서 패스워드 체킹
                .orElseThrow(() -> new UsernameNotFoundException(email));


    }

    /**
     * @Program 로그인 멤버 권한 및 활성화 체크
     * @Param   username,member
     * @Return  User
     * */
    private org.springframework.security.core.userdetails.User getUser(String username,Member member) {
        if (!member.getUse_yn().equals("1")) {
            throw new RuntimeException(username + " -> 활성화되어 있지 않습니다.");
        }
        /** 권한체크 */
        List<GrantedAuthority> grantedAuthorities = member.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(member.getEmail(), member.getPassword(), grantedAuthorities);

    }

}
