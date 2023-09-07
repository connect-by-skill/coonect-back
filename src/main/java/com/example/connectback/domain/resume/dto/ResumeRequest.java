package com.example.connectback.domain.resume.dto;

import com.example.connectback.domain.resume.entity.Education;
import com.example.connectback.domain.resume.entity.PreferKeyword;
import com.example.connectback.domain.resume.entity.WorkType;
import lombok.Data;

import java.util.List;

@Data
public class ResumeRequest {
    private String major;
    private Education education;
    private long preferIncome;
    private WorkType workType;
    private List<String> certifications;
    private List<CareerRequestDto> careers;
    private List<PreferKeyword> preferKeywords;
    private String preferJob;
}


