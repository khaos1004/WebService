package com.WebProject.WebService.common.login.service;

import com.WebProject.WebService.common.login.dto.LoginRequest;
import com.WebProject.WebService.common.login.dto.LoginResponse;
import com.WebProject.WebService.common.login.jwt.JwtUtils;
import com.WebProject.WebService.common.login.utils.CookieBuilder;
import com.WebProject.WebService.entity.User;
import com.WebProject.WebService.common.login.repository.LoginUserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final LoginUserRepository loginUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public LoginResponse login(LoginRequest request, HttpServletResponse response) {
        Optional<User> userOpt = loginUserRepository.findByEmail(request.getEmail());

        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 잘못되었습니다.");
        }

        User user = userOpt.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 잘못되었습니다.");
        }

        String accessToken = jwtUtils.createAccessToken(user.toString());
        String refreshToken = jwtUtils.createRefreshToken(user.toString());

        // 쿠키에 토큰 저장 (HttpOnly, Secure)
        Cookie accessCookie = CookieBuilder.of("accessToken", accessToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(60 * 20) // 20분
                .build();

        Cookie refreshCookie = CookieBuilder.of("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(60 * 60 * 2) // 2시간
                .build();

        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);

        return new LoginResponse("로그인 성공", user.getEmail());
    }

}
