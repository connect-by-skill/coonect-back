package com.example.connectback.global.batch.json.high_percent_accident_workplace;

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
public class HighPercentAccidentWorkplace {
    // 중대재해 발생이 규모별 동종업종 평균재해율 이상인 사업장
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int year; // 연도
    private String region; // 지역
    private String industryName; // 업종명
    private String scale; // 규모
    private String workplaceName; // 사업장명(현장명)
    private String location; // 소재지
    private int majorDisasterVictims; // 중대재해재해자수(명)
    private int workersCount; // 근로자수(명)
    private int accidentVictims; // 재해자수(명)
    private String accidentRate; // 재해율(퍼센트)
    private String industryAverageAccidentRate; // 규모별 동종업종 평균 재해율(퍼센트)

    public HighPercentAccidentWorkplace(Map<String,?> t) {
        this.year = (int) t.get("연도");
        this.region = (String) t.get("지역");
        this.industryName = (String) t.get("업종명");
        this.scale = (String) t.get("규모");
        this.workplaceName = (String) t.get("사업장명(현장명)");
        this.location = (String) t.get("소재지");
        this.majorDisasterVictims = (int) t.get("중대재해재해자수(명)");
        this.workersCount = (int) t.get("근로자수(명)");
        this.accidentVictims = (int) t.get("재해자수(명)");
        this.accidentRate = (String) t.get("재해율(퍼센트)");
        this.industryAverageAccidentRate = (String) t.get("규모별 동종업종 평균 재해율(퍼센트)");
    }
}
