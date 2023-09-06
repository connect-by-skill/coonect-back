package com.example.connectback.global.batch.json.employment_information;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Map;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmploymentInformation {
    // 장애인 고용정보
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String employmentDate; // 취업일자
    private int age; // 연령
    private String disabilityType; // 장애유형
    private String severity; // 중증여부
    private String workRegion; // 근무지역
    private String jobCategory; // 취업직종대분류

    public EmploymentInformation(Map<String,?> t) {
        this.employmentDate = (String) t.get("취업일자");
        this.age = (int) t.get("연령");
        this.disabilityType = (String) t.get("장애유형");
        this.severity = (String) t.get("중증여부");
        this.workRegion = (String) t.get("근무지역");
        this.jobCategory = (String) t.get("취업직종대분류");
    }
}
