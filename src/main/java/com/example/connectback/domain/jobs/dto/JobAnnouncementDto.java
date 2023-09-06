package com.example.connectback.domain.jobs.dto;

import com.example.connectback.domain.jobs.entity.JobAnnouncement;
import com.example.connectback.domain.member.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobAnnouncementDto {
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

    // 위시 리스트에 등록한 사용자 수
    private Long countOfMemberWish;
    // 멤버가 위시리스트에 추가 한 공고인지 확인하는 변수
    private boolean addedWishlist = false;

    public JobAnnouncementDto(JobAnnouncement jobAnnouncement) {
        this.id = jobAnnouncement.getId();
        this.applicationDate = jobAnnouncement.getApplicationDate();
        this.recruitmentPeriod = jobAnnouncement.getRecruitmentPeriod();
        this.companyName = jobAnnouncement.getCompanyName();
        this.recruitmentType = jobAnnouncement.getRecruitmentType();
        this.typeOfEmployment = jobAnnouncement.getTypeOfEmployment();
        this.formOfWages = jobAnnouncement.getFormOfWages();
        this.wage = jobAnnouncement.getWage();
        this.entryForm = jobAnnouncement.getEntryForm();
        this.requiredExperience = jobAnnouncement.getRequiredExperience();
        this.requiredEducation = jobAnnouncement.getRequiredEducation();
        this.majorField = jobAnnouncement.getMajorField();
        this.requiredCredentials = jobAnnouncement.getRequiredCredentials();
        this.businessAddress = jobAnnouncement.getBusinessAddress();
        this.companyType = jobAnnouncement.getCompanyType();
        this.responsibleAgency = jobAnnouncement.getResponsibleAgency();
        this.registrationDate = jobAnnouncement.getRegistrationDate();
        this.contactNumber = jobAnnouncement.getContactNumber();
        this.countOfMemberWish = Long.valueOf(jobAnnouncement.getMembers().size());
    }

    public JobAnnouncementDto checkAddedAnnouncement(MemberEntity member){
        Set<JobAnnouncement> announcementList = member.getWishlist();
        if (announcementList.stream().anyMatch(jobAnnouncement -> jobAnnouncement.getId().equals(this.id))){
            this.addedWishlist = true;
        }
        return this;
    }
}
