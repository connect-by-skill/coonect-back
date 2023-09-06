package com.example.connectback.global.batch.json.accident_workplace;

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
public class AccidentWorkplace {
    // 산업재해 중대산업사고 발생사업장
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int year;  // 연도
    private String workplaceName;  // 사업장명
    private String workplaceLocation;  // 사업장 소재지
    private String accidentDate;  // 재해발생일
    private String accidentDescription;  // 사고 내용
    private int fatalities;  // 사망
    private int injuries;  // 부상

    public AccidentWorkplace(Map<String,?> t) {
        this.year = (int) t.get("연도");
        this.workplaceName = (String) t.get("사업장명");
        this.workplaceLocation = (String) t.get("사업장 소재지");
        this.accidentDate = (String) t.get("재해발생일");
        this.accidentDescription = (String) t.get("사고 내용");
        this.fatalities = (int) t.get("사망");
        this.injuries = (int) t.get("부상");
    }
}
