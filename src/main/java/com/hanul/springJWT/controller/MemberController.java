package com.hanul.springJWT.controller;

import com.hanul.springJWT.dto.JoinDTO;
import com.hanul.springJWT.jwt.JwtUtil;
import com.hanul.springJWT.service.AuthenticationService;
import com.hanul.springJWT.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final AuthenticationService authenticationService;

    @GetMapping("/join")
    public String join() {
        return "join";
    }

    @PostMapping("/join")
    public String addMember(
            @Valid JoinDTO joinDTO,
            BindingResult result
    ) {

        if (result.hasErrors()) {
            System.out.println("dto 문제 발생");
            return "redirect:/login/error=dto";
        }

        System.out.println("joinDTO = " + joinDTO);
        memberService.saveMember(joinDTO);

        return "index";
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }


    @PostMapping("/login/jwt")
    public String loginJWT(String username, String password, HttpServletResponse response) {
        try {
            authenticationService.authenticateAndSetCookie(username, password, response);
            return "index";
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return "redirect:/login?errorLogin=true";
        }
    }

    @GetMapping("/my-page")
    public String myPageJWT(
            Authentication auth,
            Model model
    ) {
        if (auth == null) {
            return "redirect:/login";
        }
        model.addAttribute("username", auth.getName());
        model.addAttribute("authorities", auth.getAuthorities());
        model.addAttribute("auth", auth);
        return "myPage";
    }

}