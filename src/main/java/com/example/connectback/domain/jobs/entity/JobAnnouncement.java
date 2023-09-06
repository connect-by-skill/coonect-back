package com.example.connectback.domain.jobs.entity;

import com.example.connectback.domain.member.entity.MemberEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Entity
@Table(name = "job_announcement")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobAnnouncement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_announcement_id")
    // 연번
    private Long id;
    // 구인 신청일자
    private String applicationDate;
    // 모집 기간
    private String recruitmentPeriod;
    // 사업장명
    private String companyName;
    // 모집직종
    private String recruitmentType;
    // 고용형태
    private String typeOfEmployment;
    // 임금형태
    private String formOfWages;
    // 임금
    private int wage;
    // 입사형태
    private String entryForm;
    // 요구경력
    private String requiredExperience;
    // 요구학력
    private String requiredEducation;
    // 전공 계열
    private String majorField;
    // 요구자격증
    private String requiredCredentials;
    // 사업장 주소
    private String businessAddress;
    // 기업 형태
    private String companyType;
    // 담당기관
    private String responsibleAgency;
    // 등록일
    private String registrationDate;
    // 연락처
    private String contactNumber;


    @ManyToMany(mappedBy = "wishlist")
    private Set<MemberEntity> members = new HashSet<>();

    public JobAnnouncement(Map<String,?> map) {
        this.applicationDate = (String) map.get("구인신청일자");
        String allRecruitmentPeriod = (String) map.get("모집기간");
        this.recruitmentPeriod = allRecruitmentPeriod.substring(11);
        this.companyName = (String) map.get("사업장명");
        this.recruitmentType = (String) map.get("모집직종");
        this.typeOfEmployment = (String) map.get("고용형태");
        this.formOfWages = (String) map.get("임금형태");
        this.wage = (int) map.get("임금");
        this.entryForm = (String) map.get("입사형태");
        this.requiredExperience = (String) map.get("요구경력");
        this.requiredEducation = (String) map.get("요구학력");
        this.majorField = (String) map.get("전공계열");
        this.requiredCredentials = (String) map.get("요구자격증");
        this.businessAddress = (String) map.get("사업장 주소");
        this.companyType = (String) map.get("기업형태");
        this.responsibleAgency = (String) map.get("담당기관");
        this.registrationDate = (String) map.get("등록일");
        this.contactNumber = (String) map.get("연락처");
    }

}
