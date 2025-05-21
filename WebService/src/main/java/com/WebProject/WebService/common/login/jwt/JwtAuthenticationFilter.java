// JwtAuthenticationFilter.java
package com.WebProject.WebService.common.login.jwt;

import com.WebProject.WebService.entity.User;
import com.WebProject.WebService.common.login.repository.LoginUserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final LoginUserRepository loginUserRepository;
    private final JwtUtils jwtUtils;

    private String getTokenFromCookies(HttpServletRequest request, String name) {
        if (request.getCookies() == null) return null;
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(name)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    private void setAuthenticationContext(String token, HttpServletRequest request) {
        Claims claims = jwtUtils.getClaims(token);
        String email = claims.getSubject();

        Optional<User> userOpt = loginUserRepository.findByEmail(email);
        if (userOpt.isEmpty()) return;

        User user = userOpt.get();
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                user, null, user.getAuthorities()
        );
        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        System.out.println("path::" + path);

        // 인증 없이 허용할 경로 목록
        List<String> publicPaths = List.of(
                "/user/signup", "/user/login", "/user/refresh",
                "/api/user/signup", "/api/user/login", "/api/user/refresh"
        );

        // 만약 현재 요청이 예외 경로 중 하나로 시작하면 필터 건너뜀
        if (publicPaths.stream().anyMatch(path::startsWith)) {
            filterChain.doFilter(request, response);
            return;
        }


        String accessToken = getTokenFromCookies(request, "accessToken");
        String refreshToken = getTokenFromCookies(request, "refreshToken");

        if (accessToken != null && jwtUtils.validateToken(accessToken)) {
            setAuthenticationContext(accessToken, request);
        } else if (refreshToken != null && jwtUtils.validateToken(refreshToken)) {
            // Access Token 재발급
            String email = jwtUtils.getEmail(refreshToken);
            String newAccessToken = jwtUtils.createAccessToken(email);

            Cookie newAccessCookie = new Cookie("accessToken", newAccessToken);
            newAccessCookie.setHttpOnly(true);
            newAccessCookie.setSecure(true);
            newAccessCookie.setPath("/");
            newAccessCookie.setMaxAge(60 * 20); // 20분

            response.addCookie(newAccessCookie);

            setAuthenticationContext(newAccessToken, request);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
