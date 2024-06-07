package com.hanul.springJWT.service;

import com.hanul.springJWT.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomAuthenticationService {

    private final AuthenticationService authenticationService;
    private final CookieService cookieService;

    public void authenticateAndSetCookie(String username, String password, HttpServletResponse response) {
        try {
            // 사용자 인증
            Authentication authentication = authenticationService.authenticate(username, password);

            // 인증이 성공하면 SecurityContextHolder에 인증 정보를 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // JWT 생성
            String jwtToken = JwtUtil.createToken(authentication);

            // 생성한 JWT를 쿠키에 저장
            cookieService.addJwtCookie(response, jwtToken);

        } catch (Exception e) {
            throw new RuntimeException("로그인 증명 실패: " + e.getMessage());
        }
    }

}
