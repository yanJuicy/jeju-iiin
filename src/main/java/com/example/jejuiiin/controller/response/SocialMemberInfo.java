package com.example.jejuiiin.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocialMemberInfo {

    private String id;
    private String email;
    private String name;

}
