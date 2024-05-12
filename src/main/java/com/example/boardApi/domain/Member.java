package com.example.boardApi.domain;

import com.example.boardApi.dto.MemberDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String email;
    private String username;
    private String password;
    private String use_yn;
    private int age;

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "member_id", referencedColumnName = "member_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;

    @OneToMany(mappedBy = "member")
    private List<Board> boardList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Reply> replyList = new ArrayList<>();

    public Member(MemberDto memberDto) {
        this.id = memberDto.getId();
        this.email = memberDto.getEmail();
        this.username = memberDto.getUsername();
        this.password = memberDto.getPassword();
        this.use_yn = memberDto.getUse_yn();
        this.age = memberDto.getAge();
    }
}
