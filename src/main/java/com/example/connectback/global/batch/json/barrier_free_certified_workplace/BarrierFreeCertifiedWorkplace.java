package com.example.connectback.global.batch.json.barrier_free_certified_workplace;

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
public class BarrierFreeCertifiedWorkplace {
    // 배리어프리 인증 시설
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String facilityName; // 시설물명
    private String target; // 인증대상
    private String certificationType; // 인증구분
    private String grade; // 등급
    private String regionCode; // 지역코드
    private String city; // 시군구
    private String certificationIssuedDate; // 인증발급일
    private String certificationExpirationDate; // 인증만료일
    private String workplaceType; // 사업장구분
    private String certificationCancellationStatus; // 인증만료(취소)여부

    public BarrierFreeCertifiedWorkplace(Map<String, String> t) {
        this.facilityName = t.get("시설물명");
        this.target = t.get("인증대상");
        this.certificationType = t.get("인증구분");
        this.grade = t.get("등급");
        this.regionCode = t.get("지역코드");
        this.city = t.get("시군구");
        this.certificationIssuedDate = t.get("인증발급일");
        this.certificationExpirationDate = t.get("인증만료일");
        this.workplaceType = t.get("사업장구분");
        this.certificationCancellationStatus = t.get("인증만료(취소)여부");
    }
}
