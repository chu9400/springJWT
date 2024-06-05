package com.hanul.springJWT.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("JwtFilter 실행");

        // 쿠키 확인
        Cookie[] cookies = request.getCookies();
        if (cookies == null){
            filterChain.doFilter(request, response);
            return;
        }

        // jwt 쿠키 확인
        var jwtCookie = "";
        for (int i = 0; i < cookies.length; i++){
            if (cookies[i].getName().equals("jwt")){
                jwtCookie = cookies[i].getValue();
            }
        }

        if (jwtCookie == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // jwt 해석
        Claims claim;
        try {
            claim = JwtUtil.extractToken(jwtCookie);
        } catch (Exception e) {
            log.info("e = {}", e.getMessage());
            filterChain.doFilter(request, response);
            return;
        }

        // jwt 내부에 있는 정보들 해석
        String[] arr = claim.get("authorities").toString().split(",");
        var authorities = Arrays.stream(arr)
                .map(a -> new SimpleGrantedAuthority(a)).toList();
        String username = claim.get("username").toString();

        // auth 변수에 등록할 값 생성
        var authToken = new UsernamePasswordAuthenticationToken(
                username, null, authorities
        );

        // auth 변수에 등록
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // 다음 필터 실행
        filterChain.doFilter(request, response);

    }
}
