package com.hanul.springJWT.service;

import com.hanul.springJWT.dto.JoinDTO;
import com.hanul.springJWT.entity.MemberEntity;
import com.hanul.springJWT.repository.MemberEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberEntityRepository memberEntityRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public void saveMember(JoinDTO joinDTO) {

        Boolean existsedByUsername = memberEntityRepository.existsByUsername(joinDTO.getUsername());

        if (existsedByUsername) {
            System.out.println("existsedByUsername = " + existsedByUsername);
            System.out.println("이미 아이디가 있습니다.");
            return;
        }

        String encodePassword = bCryptPasswordEncoder.encode(joinDTO.getPassword());

        MemberEntity memberEntity = new MemberEntity(
                joinDTO.getUsername(),
                encodePassword,
                joinDTO.getDisplayName()
        );

        memberEntityRepository.save(memberEntity);
    }
}
