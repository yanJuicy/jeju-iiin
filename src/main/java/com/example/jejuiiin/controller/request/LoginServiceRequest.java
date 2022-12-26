package com.example.jejuiiin.controller.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginServiceRequest {
    private String loginId;
    private String password;

    @Builder
    public LoginServiceRequest(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }
}
