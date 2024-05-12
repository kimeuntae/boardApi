package com.example.boardApi.enums;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.security.core.GrantedAuthority;


public enum roleEnum implements GrantedAuthority {
    ROLE_ADMIN("관리자"), ROLE_USER("사용자");

    private String roleName;

    roleEnum(String roleName) {
        this.roleName = roleName;
    }
    public String getRoleName() {
        return roleName;
    }
    public String getAuthority() {
        return name();
    }
}
