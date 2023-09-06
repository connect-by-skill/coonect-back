package com.example.connectback.global.batch.json.risk_assessment_certified_workplace;

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
public class RiskAssessmentCertifiedWorkplace {
    // 위험성 평가 인정사업장 정보
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 노동지청명
    private String districtOfficeName;
    // 사업장명
    private String businessName;
    // 공사장명
    private String nameOfConstructionSite;
    // 인정일
    private String recognitionDate;

    public RiskAssessmentCertifiedWorkplace(Map<String,String> map) {
        this.districtOfficeName = map.get("노동지청명");
        this.businessName = map.get("사업장명");
        this.nameOfConstructionSite = map.get("공사장명");
        this.recognitionDate = map.get("인정일");
    }
}
