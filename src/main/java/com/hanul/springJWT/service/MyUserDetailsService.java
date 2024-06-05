package com.hanul.springJWT.service;

import com.hanul.springJWT.entity.MemberEntity;
import com.hanul.springJWT.repository.MemberEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final MemberEntityRepository memberEntityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // DB에 username 확인
        Optional<MemberEntity> memberOptional = memberEntityRepository.findByUsername(username);
        if (memberOptional.isEmpty()) {
            throw new UsernameNotFoundException("로그인 아이디를 찾을 수 없습니다.");
        }

        MemberEntity member = memberOptional.get();
        List<GrantedAuthority> authorities = new ArrayList<>();

        // 권한 추가
        if ("admin".equals(member.getUsername()) || "kim".equals(member.getUsername())) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return new User(member.getUsername(), member.getPassword(), authorities);

    }
}
