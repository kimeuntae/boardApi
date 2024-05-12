package com.example.boardApi.enums;

public enum httpEnum {
    AUTHORIZATION_HEADER("Authorization"), AUTHORITIES_KEY("auth");
    private final String value;

    httpEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
