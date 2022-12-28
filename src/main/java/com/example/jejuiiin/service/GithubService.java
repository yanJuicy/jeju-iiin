package com.example.jejuiiin.service;

import com.example.jejuiiin.controller.exception.OAuth2LoginException;
import com.example.jejuiiin.domain.Member;
import com.example.jejuiiin.domain.SocialType;
import com.example.jejuiiin.repository.MemberRepository;
import com.example.jejuiiin.security.jwt.JwtUtil;
import com.example.jejuiiin.service.vo.GithubUserInfo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;

import java.util.Optional;
import java.util.UUID;

import static com.example.jejuiiin.security.jwt.JwtUtil.AUTHORIZATION_ACCESS;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Slf4j
@RequiredArgsConstructor
@Service
public class GithubService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	@Value("${github.client-id}")
	private String clientId;

	@Value("${github.client-secret}")
	private String clientSecret;

	@Value("${github.redirect-uri}")
	private String redirectUri;

	@Transactional
	public void githubLogin(String code, HttpServletResponse httpServletResponse) {
		String githubAccessToken = getAccessToken(code);
		GithubUserInfo githubUserInfo = getGithubUserInfo(githubAccessToken);

		Member member = register(githubUserInfo);
		String accessToken = jwtUtil.createAccessToken(member.getLoginId());
		httpServletResponse.addHeader(AUTHORIZATION_ACCESS, accessToken);
	}

	private String getAccessToken(String code) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8");
		headers.add(ACCEPT, "application/json");

		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("client_id", clientId);
		body.add("client_secret", clientSecret);
		body.add("code", code);
		body.add("redirect_uri", redirectUri);

		/* github 인증 서버에 access-token 요청  */
		HttpEntity<MultiValueMap<String, String>> githubTokenRequest = new HttpEntity<>(body, headers);
		JsonNode jsonNode = getJsonNode(githubTokenRequest, "https://github.com/login/oauth/access_token", POST);
		return jsonNode.get("access_token").asText();
	}

	private GithubUserInfo getGithubUserInfo(String accessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8");
		headers.add(ACCEPT, "application/json");
		headers.add(AUTHORIZATION, "Bearer " + accessToken);

		/* github 인증 서버에 유저 정보 요청  */
		HttpEntity<MultiValueMap<String, String>> githubUserInfoRequest = new HttpEntity<>(headers);
		JsonNode jsonNode = getJsonNode(githubUserInfoRequest, "https://api.github.com/user", GET);

		String login = jsonNode.get("login").asText();
		String name = jsonNode.get("name").asText();
		String email = jsonNode.get("email").asText();

		/* email이 private 설정일 경우 email 정보만 재요청, github는 email이 private 처리돼 있음 */
		if (email.equals("null")) {
			/* github 인증 서버에 유저 email 정보 요청  */
			HttpEntity<MultiValueMap<String, String>> emailRequest = new HttpEntity<>(headers);
			jsonNode = getJsonNode(emailRequest, "https://api.github.com/user/public_emails", GET);
			email = jsonNode.get(1).get("email").asText();
		}

		return new GithubUserInfo(login, name, email);
	}

	private Member register(GithubUserInfo githubUserInfo) {
		String githubEmail = githubUserInfo.email();
		Optional<Member> savedMember = memberRepository.findByEmail(githubEmail);
		if (savedMember.isPresent()) {
			return savedMember.get();
		}

		/* 처음 깃허브 로그인할 경우 내부 회원가입 실행 */
		String password = UUID.randomUUID().toString();
		String encodedPassword = passwordEncoder.encode(password);

		Member member = Member.builder()
				.loginId(githubUserInfo.loginId())
				.password(encodedPassword)
				.name(githubUserInfo.name())
				.email(githubEmail)
				.isSocial(true)
				.socialType(SocialType.GITHUB)
				.build();
		return memberRepository.save(member);
	}

	private JsonNode getJsonNode(HttpEntity<MultiValueMap<String, String>> request, String url, HttpMethod httpMethod) {
		RestTemplate rt = new RestTemplate();
		ResponseEntity<String> response = rt.exchange(url, httpMethod, request, String.class);
		String responseBody = response.getBody();

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			JsonNode jsonNode = objectMapper.readTree(responseBody);
			if (jsonNode.get("error") != null) {
				String errorDescription = jsonNode.get("error_description").asText();
				throw new OAuth2LoginException(errorDescription);
			}
			return jsonNode;
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
			throw new OAuth2LoginException(e.getMessage());
		}
	}

}
