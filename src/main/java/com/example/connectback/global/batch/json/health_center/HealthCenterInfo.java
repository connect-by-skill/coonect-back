package com.example.connectback.global.batch.json.health_center;


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
public class HealthCenterInfo {
    // 근로자 건강센터 현황정보
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String location1;
    private String location2;
    private String contact1;
    private String contact2;
    private String fax1;
    private String fax2;
    private String centerContact;
    private String centerFax;
    private String centerName;
    private String address;
    private String website;

    public HealthCenterInfo(Map<String, String> t) {
        this.location1 = t.get("분소 소재지1");
        this.location2 = t.get("분소 소재지2");
        this.contact1 = t.get("분소 연락처1");
        this.contact2 = t.get("분소 연락처2");
        this.fax1 = t.get("분소 팩스1");
        this.fax2 = t.get("분소 팩스2");
        this.centerContact = t.get("센터 연락처");
        this.centerFax = t.get("센터 팩스");
        this.centerName = t.get("센터명");
        this.address = t.get("소재지");
        this.website = t.get("홈페이지");
    }
}
