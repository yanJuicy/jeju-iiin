package com.example.jejuiiin.controller;

import com.example.jejuiiin.controller.response.HeaderResponse;
import com.example.jejuiiin.controller.response.Response;
import com.example.jejuiiin.service.HeaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/header")
public class HeaderController {
    private final HeaderService headerService;

    @GetMapping("/")
    public Response<HeaderResponse> showHeaderDetails(@AuthenticationPrincipal UserDetails userDetails){
        HeaderResponse headerResponse = headerService.showHeaderDetails(userDetails);
        return new Response<>(200, "헤더가 반환되었습니다.", headerResponse);
    }
}
