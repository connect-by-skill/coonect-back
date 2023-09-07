package com.example.connectback.domain.resume.repository;

import com.example.connectback.domain.resume.entity.ResumeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResumeRepository extends JpaRepository<ResumeEntity, Long> {
    Optional<ResumeEntity> findByMember_Email(String userEmail);
    boolean existsByMember_Email(String userEmail);
    void deleteByMember_Email(String userEmail);
}
