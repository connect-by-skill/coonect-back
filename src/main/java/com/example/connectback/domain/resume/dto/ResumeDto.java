package com.example.connectback.domain.resume.dto;

import com.example.connectback.domain.resume.entity.*;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ResumeDto {
    // 전공
    private String major;
    // 최종 학력
    private Education education;
    // 선호 연봉
    private long preferIncome;
    // 선호 근무 형태
    private WorkType workType;
    // 경력
    private List<CareerDto> careerList;
    // 자격증
    private List<String> certifications;
    // 선호 키워드
    private List<PreferKeyword> preferKeywords;
    private String preferJob;

    public ResumeDto(ResumeEntity entity) {
        this.major = entity.getMajor();
        this.education = entity.getEducation();
        this.preferIncome = entity.getPreferIncome();
        this.workType = entity.getWorkType();
        this.careerList = entity.getCareerList().stream().map(CareerDto::new).collect(Collectors.toList());
        this.certifications = Certification.getDisplayNameList(entity.getCertifications());
        this.preferKeywords = entity.getPreferKeywords();
        this.preferJob = entity.getPreferJob().getDisplayName();
    }
}
