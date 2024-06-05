package com.hanul.springJWT.repository;

import com.hanul.springJWT.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberEntityRepository extends JpaRepository<MemberEntity, Long> {
    Boolean existsByUsername(String username);
    Optional<MemberEntity> findByUsername(String username);
}
