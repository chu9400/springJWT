package com.hanul.springJWT.service;

import com.hanul.springJWT.jwt.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public void authenticateAndSetCookie(String username, String password, HttpServletResponse response) {
        try {
            // 사용자가 입력한 username과 password로 인증 토큰 생성
            var authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

            // AuthenticationManager를 통해 인증 시도
            var authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            // 인증이 성공하면 SecurityContextHolder에 인증 정보를 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // JWT 생성
            var jwtToken = JwtUtil.createToken(authentication);

            // 생성한 JWT를 쿠키에 저장
            var jwtCookie = new Cookie("jwt", jwtToken);
            jwtCookie.setMaxAge(1000);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setPath("/");
            response.addCookie(jwtCookie);

        } catch (Exception e) {
            throw new RuntimeException("로그인 증명 실패: " + e.getMessage());
        }
    }
}
