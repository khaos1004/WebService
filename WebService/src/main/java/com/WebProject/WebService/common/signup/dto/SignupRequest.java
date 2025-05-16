package com.WebProject.WebService.common.signup.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignupRequest {
//    private String id;
    private String email;
    private String password;
    private String name;
}