package com.example.jejuiiin.mapper;

import com.example.jejuiiin.controller.request.SignupServiceRequest;
import com.example.jejuiiin.domain.Member;

public class MemberMapper {

    /* Signup Dto를 Member 엔티티로 매핑 */
    public static Member signupServiceRequestToMember(SignupServiceRequest signupServiceRequest, String password){
        return Member.builder()
                .loginId(signupServiceRequest.getLoginId())
                .password(password)
                .name(signupServiceRequest.getName())
                .email(signupServiceRequest.getEmail())
                .isSocial(signupServiceRequest.isSocial())
                .socialType(signupServiceRequest.getSocialType())
                .build();
    }
}
