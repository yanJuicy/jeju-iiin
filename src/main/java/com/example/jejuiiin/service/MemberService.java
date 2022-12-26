package com.example.jejuiiin.service;

import com.example.jejuiiin.controller.exception.DuplicateException;
import com.example.jejuiiin.controller.request.LoginServiceRequest;
import com.example.jejuiiin.controller.request.SignupServiceRequest;
import com.example.jejuiiin.domain.Member;

import com.example.jejuiiin.mapper.MemberMapper;
import com.example.jejuiiin.repository.MemberRepository;
import com.example.jejuiiin.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.example.jejuiiin.controller.exception.ExceptionMessage.DIFFERENT_PASSWORD_MSG;
import static com.example.jejuiiin.controller.exception.ExceptionMessage.DUPLICATE_EMAIL_MSG;
import static com.example.jejuiiin.controller.exception.ExceptionMessage.DUPLICATE_LOGIN_ID_MSG;
import static com.example.jejuiiin.controller.exception.ExceptionMessage.LOGIN_ID_NOT_FOUND_MSG;
import static com.example.jejuiiin.security.jwt.JwtUtil.AUTHORIZATION_ACCESS;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    /* 아이디 중복 검사, 중복일 경우 true, 중복이 아닐 경우 false 반환 */
    private boolean isDuplicateLoginId(String loginId){
        Optional<Member> memberOptional = memberRepository.findByLoginId(loginId);
        return memberOptional.isPresent();
    }

    /* 이메일 중복 검사, 중복일 경우 true, 중복이 아닐 경우 false 반환 */
    private boolean isDuplicateEmail(String email){
        Optional<Member> memberOptional = memberRepository.findByEmail(email);
        return memberOptional.isPresent();
    }

    /* 회원가입 */
    @Transactional
    public void signup(SignupServiceRequest signupServiceRequest) {
        String loginId = signupServiceRequest.getLoginId();
        String email = signupServiceRequest.getEmail();
        String password = signupServiceRequest.getPassword();

        /* 아이디와 중복 확인 */
        if(isDuplicateLoginId(loginId)){
            throw new DuplicateException(DUPLICATE_LOGIN_ID_MSG);
        }

        if(isDuplicateLoginId(loginId)){
            throw new DuplicateException(DUPLICATE_EMAIL_MSG);
        }

        /* 비밀번호 암호화 */
        String encodedPassword = passwordEncoder.encode(password);

        Member member = MemberMapper.signupServiceRequestToMember(signupServiceRequest, encodedPassword);
        memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public void login(LoginServiceRequest loginServiceRequest, HttpServletResponse httpServletResponse) {
        String loginId = loginServiceRequest.getLoginId();
        String password = loginServiceRequest.getPassword();

        /* 일치하는 아이디가 없을 경우 예외 반환 */
        Member member = memberRepository.findByLoginId(loginId).
                orElseThrow(() -> new NoSuchElementException(LOGIN_ID_NOT_FOUND_MSG.getMsg())
                );

        /* 아이디가 일치하는데 비밀번호가 다를 경우 예외 반환 */
        if(!passwordEncoder.matches(password, member.getPassword())){
            throw new NoSuchElementException(DIFFERENT_PASSWORD_MSG.getMsg());
        }

        String accessToken = jwtUtil.createAccessToken(loginId);

        httpServletResponse.addHeader(AUTHORIZATION_ACCESS, accessToken);
    }
}
