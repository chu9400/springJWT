package com.hanul.springJWT.service;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class CookieService {

    public void addJwtCookie(HttpServletResponse response, String jwtToken) {
        var jwtCookie = new Cookie("jwt", jwtToken);
        jwtCookie.setMaxAge(1000); // 유효기간
        jwtCookie.setHttpOnly(true); // 보안 up
        jwtCookie.setPath("/"); // 모든 경로에서 쿠키 사용
        response.addCookie(jwtCookie); // 브라우저 쿠키에 저장
    }
}
