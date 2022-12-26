package com.example.jejuiiin.controller.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {
    private String loginId;
    private String password;

    @Builder
    public LoginRequest(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }
}
