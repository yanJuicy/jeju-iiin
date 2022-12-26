package com.example.jejuiiin.controller;

import com.example.jejuiiin.controller.request.SignupRequest;
import com.example.jejuiiin.controller.response.Response;
import com.example.jejuiiin.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public Response signup(@Validated @RequestBody SignupRequest request){
        Signup signup = Signup.builder()
                .loginId(request.getLoginId())
                .password(request.getPassword())
                .name(request.getName())
                .email(request.getEmail())
                .isSocial(false)
                .socialType(null)
                .build();

        MemberService.signup(signup);

        return new Response(201, "회원가입이 완료되었습니다.", null);
    }
}
