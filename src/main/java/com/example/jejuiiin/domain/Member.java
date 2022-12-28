package com.example.jejuiiin.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false, unique = true)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private boolean isSocial;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Builder
    public Member(String loginId, String password, String name, String email, boolean isSocial, SocialType socialType) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.isSocial = isSocial;
        this.socialType = socialType;
    }

    public Member socialType(SocialType socialType) {
        this.socialType = socialType;
        return this;
    }
}
