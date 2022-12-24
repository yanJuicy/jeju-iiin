package com.example.jejuiiin.security;

import com.example.jejuiiin.domain.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

//인증 객체
public class UserDetailsImpl implements UserDetails {
    private final Member member;     // 인증완료된 User 객체
    private final String loginId;     // 인증완료된 User의 ID
    private final String password;     // 인증완료된 User의 PWD

    public UserDetailsImpl(Member member, String loginId, String password) {
        this.member = member;
        this.loginId = loginId;
        this.password = password;
    }

    //  인증완료된 User 를 가져오는 Getter
    public Member getMember() {
        return member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.loginId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
