package com.example.jejuiiin.controller;

import com.example.jejuiiin.controller.request.LoginRequest;
import com.example.jejuiiin.controller.request.LoginServiceRequest;
import com.example.jejuiiin.controller.request.SignupRequest;
import com.example.jejuiiin.controller.request.SignupServiceRequest;
import com.example.jejuiiin.controller.response.Response;
import com.example.jejuiiin.mapper.MemberMapper;
import com.example.jejuiiin.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public Response signup(@Validated @RequestBody SignupRequest signupRequest){
        SignupServiceRequest signupServiceRequest = MemberMapper.signupRequestToSignUpServiceRequest(signupRequest);

        memberService.signup(signupServiceRequest);

        return new Response(201, "회원가입이 완료되었습니다.", null);
    }

    @PostMapping("/login")
    public Response login(@RequestBody LoginRequest loginRequest, HttpServletResponse httpServletResponse){
        LoginServiceRequest loginServiceRequest = MemberMapper.loginRequestToLoginServiceRequest(loginRequest);

        memberService.login(loginServiceRequest, httpServletResponse);

        return new Response(200, "로그인이 완료되었습니다.", null);

    }
}
