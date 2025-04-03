package com.WebProject.WebService.service;

import com.WebProject.WebService.dto.login.JwtResponse;
import com.WebProject.WebService.dto.login.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthBusiness authBusiness;

    public JwtResponse login(LoginRequest loginRequest) {
        return authBusiness.authenticateAndGenerateToken(loginRequest);
    }
}
