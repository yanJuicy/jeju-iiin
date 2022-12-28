package com.example.jejuiiin.controller;

import com.example.jejuiiin.controller.request.GithubLoginRequest;
import com.example.jejuiiin.controller.response.Response;
import com.example.jejuiiin.service.GithubService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
@RestController
public class OAuth2Controller {

	private final GithubService githubService;

	@PostMapping("/login/github")
	public Response<?> github(@RequestBody GithubLoginRequest request, HttpServletResponse httpServletResponse) {
		githubService.githubLogin(request.getCode(), httpServletResponse);
		return new Response<>(200, "깃허브 로그인이 완료되었습니다.", null);
	}

}
