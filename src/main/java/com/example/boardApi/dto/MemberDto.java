package com.example.boardApi.dto;

import com.example.boardApi.domain.Board;
import com.example.boardApi.domain.Member;
import com.example.boardApi.domain.Reply;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MemberDto {

    @Size(min = 3, max = 50)
    private Long id;
    @NotNull
    @Size(min = 3, max = 50)
    private String email;
    @NotNull
    @Size(min = 3, max = 50)
    private String username;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 3, max = 50)
    private String password;

    private String use_yn;
    private int age;

    public MemberDto() {
    }

    public MemberDto(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.username = member.getUsername();
        this.password = member.getPassword();
        this.use_yn = member.getUse_yn();
        this.age = member.getAge();
    }
}
