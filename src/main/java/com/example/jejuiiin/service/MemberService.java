package com.example.jejuiiin.service;

import com.example.jejuiiin.controller.request.SignupServiceRequest;
import com.example.jejuiiin.domain.Member;
import com.example.jejuiiin.exception.DuplicateException;
import com.example.jejuiiin.mapper.MemberMapper;
import com.example.jejuiiin.repository.MemberRepository;
import com.example.jejuiiin.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.jejuiiin.exception.ExceptionMessage.DUPLICATE_EMAIL_MSG;
import static com.example.jejuiiin.exception.ExceptionMessage.DUPLICATE_LOGIN_ID_MSG;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    /* 아이디 중복 검사, 중복이 아닐 경우 true 반환 */
    private boolean checkDuplicateLoginId(String loginId){
        memberRepository.findByLoginId(loginId)
                .ifPresent(m->{
                    throw new DuplicateException(DUPLICATE_LOGIN_ID_MSG);}
                );
        return true;
    }

    /* 이메일 중복 검사, 중복이 아닐 경우 true 반환 */
    private boolean checkDuplicateEmail(String email){
        memberRepository.findByEmail(email)
                .ifPresent(m->{
                    throw new DuplicateException(DUPLICATE_EMAIL_MSG);}
                );
        return true;
    }

    /* 회원가입 */
    @Transactional
    public void signup(SignupServiceRequest signupServiceRequest) {
        String loginId = signupServiceRequest.getLoginId();
        String email = signupServiceRequest.getEmail();
        String password = signupServiceRequest.getPassword();

        /* 아이디와 이메일 중복 확인 */
        checkDuplicateLoginId(loginId);
        checkDuplicateEmail(email);

        /* 비밀번호 암호화 */
        String encodedPassword = passwordEncoder.encode(password);

        Member member = MemberMapper.signupServiceRequestToMember(signupServiceRequest, encodedPassword);
        memberRepository.save(member);
    }
}
