package com.example.connectback.domain.resume.service;


import com.example.connectback.domain.member.entity.MemberEntity;
import com.example.connectback.domain.member.repository.MemberRepository;
import com.example.connectback.domain.resume.dto.CareerRequestDto;
import com.example.connectback.domain.resume.dto.ResumeDto;
import com.example.connectback.domain.resume.dto.ResumeRequest;
import com.example.connectback.domain.resume.entity.Career;
import com.example.connectback.domain.resume.entity.Certification;
import com.example.connectback.domain.resume.entity.JobCategory;
import com.example.connectback.domain.resume.entity.ResumeEntity;
import com.example.connectback.domain.resume.exception.DuplicateResumeException;
import com.example.connectback.domain.resume.exception.NotExistResumeException;
import com.example.connectback.domain.resume.repository.ResumeRepository;
import com.example.connectback.global.error.exception.EntityNotFoundException;
import com.example.connectback.global.error.exception.ErrorCode;
import com.example.connectback.global.error.exception.InvalidValueException;
import com.example.connectback.global.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResumeService {
    private final ResumeRepository resumeRepository;
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    public void deleteResume(String token){
        String userEmail = tokenProvider.validateJwtAndGetUserEmail(token);
        MemberEntity member = memberRepository.findByEmail(userEmail)
                .orElseThrow(()->new EntityNotFoundException("사용자를 찾을 수 없습니다."));
        if (!resumeRepository.existsByMember_Email(userEmail)){
            throw new NotExistResumeException(ErrorCode.RESUME_NOT_FOUND);
        }

        resumeRepository.deleteByMember_Email(userEmail);

        return;
    }

    @Transactional
    public ResumeDto createResume(String token, ResumeRequest resumeRequest){
        String userEmail = tokenProvider.validateJwtAndGetUserEmail(token);
        MemberEntity member = memberRepository.findByEmail(userEmail)
                .orElseThrow(()->new EntityNotFoundException("사용자를 찾을 수 없습니다."));
        if (resumeRepository.existsByMember_Email(userEmail)){
            throw new DuplicateResumeException(ErrorCode.DUPLICATE_RESUME);
        }

        ResumeEntity resume = ResumeEntity.builder()
                .member(member)
                .preferIncome(resumeRequest.getPreferIncome())
                .workType(resumeRequest.getWorkType())
                .education(resumeRequest.getEducation())
                .major(resumeRequest.getMajor())
                .careerList(new ArrayList<>())
                .certifications(Certification.findByDisplayNameList(resumeRequest.getCertifications()))
                .preferKeywords(resumeRequest.getPreferKeywords())
                .preferJob(JobCategory.findByDisplayName(resumeRequest.getPreferJob()))
                .build();

        // 경력 생성 및 추가
        List<Career> careers = new ArrayList<>();
        for (CareerRequestDto careerRequest : resumeRequest.getCareers()) {
            Career career = Career.builder()
                    .period(careerRequest.getPeriod())
                    .category(JobCategory.findByDisplayName(careerRequest.getCategory()))
                    .build();
            career.setResume(resume);
            careers.add(career);
        }
        resume.setCareerList(careers);

        ResumeEntity savedResume = resumeRepository.save(resume);

        member.writeResume(resume);
        memberRepository.save(member);

        ResumeDto response = Optional.ofNullable(savedResume).map(ResumeDto::new)
            .orElseThrow(()->new InvalidValueException("dto 변환 실패"));

        return response;
    }

    public ResumeDto getResume(String accessToken) {
        String userEmail = tokenProvider.validateJwtAndGetUserEmail(accessToken);
        Optional<ResumeEntity> resumeEntity = resumeRepository.findByMember_Email(userEmail);
        if(resumeEntity.isEmpty()){
            throw new NotExistResumeException(ErrorCode.RESUME_NOT_FOUND);
        }
        ResumeEntity currentEntity = resumeEntity.get();
        ResumeDto response = Optional.ofNullable(currentEntity).map(ResumeDto::new)
                .orElseThrow(()->new InvalidValueException("dto 변환 실패"));


        return response;
    }
}
