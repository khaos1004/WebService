package com.WebProject.WebService.common.login.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class LoginRequest {
    private String email;
    private String password;
}
