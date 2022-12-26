package com.example.jejuiiin.mapper;

import com.example.jejuiiin.controller.request.LoginRequest;
import com.example.jejuiiin.controller.request.LoginServiceRequest;
import com.example.jejuiiin.controller.request.SignupRequest;
import com.example.jejuiiin.controller.request.SignupServiceRequest;
import com.example.jejuiiin.domain.Member;

public class MemberMapper {

    /* SignupServiceRequest Dto를 Member 엔티티로 매핑 */
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

    /* SignupRequest Dto를 SignupServiceRequest Dto로 매핑 */
    public static SignupServiceRequest signupRequestToSignUpServiceRequest(SignupRequest signupRequest){
        return SignupServiceRequest.builder()
                .loginId(signupRequest.getLoginId())
                .password(signupRequest.getPassword())
                .name(signupRequest.getName())
                .email(signupRequest.getEmail())
                .isSocial(false)
                .socialType(null)
                .build();
    }

    /* Login Request Dto를 LoginServiceRequest Dto로 매핑 */
    public static LoginServiceRequest loginRequestToLoginServiceRequest(LoginRequest loginRequest){
        return LoginServiceRequest.builder()
                .loginId(loginRequest.getLoginId())
                .password(loginRequest.getPassword())
                .build();
    }
}
