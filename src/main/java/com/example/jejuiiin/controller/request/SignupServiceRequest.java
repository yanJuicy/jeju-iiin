package com.example.jejuiiin.controller.request;

import com.example.jejuiiin.domain.SocialType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class SignupServiceRequest {
    private String loginId;
    private String password;
    private String name;
    private String email;
    private boolean isSocial;
    private SocialType socialType;

    @Builder
    public SignupServiceRequest(String loginId, String password, String name, String email, boolean isSocial, SocialType socialType) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.isSocial = isSocial;
        this.socialType = socialType;
    }
}
