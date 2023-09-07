package com.example.connectback.domain.jobs.service;

import com.example.connectback.domain.jobs.dto.JobAnnouncementDto;
import com.example.connectback.domain.jobs.entity.JobAnnouncement;
import com.example.connectback.domain.jobs.repository.JobAnnouncementRepository;
import com.example.connectback.domain.member.entity.MemberEntity;
import com.example.connectback.domain.member.repository.MemberRepository;
import com.example.connectback.global.error.exception.EntityNotFoundException;
import com.example.connectback.global.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobAnnouncementService {
    private final JobAnnouncementRepository jobAnnouncementRepository;
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    public void addToWishlist(String accessToken, Long jobAnnouncementId) {
        String email = tokenProvider.validateJwtAndGetUserEmail(accessToken);
        MemberEntity member = memberRepository.findByEmail(email)
                .orElseThrow(()-> new EntityNotFoundException("사용자를 찾을 수 없습니다"));

        JobAnnouncement jobAnnouncement = jobAnnouncementRepository.findById(jobAnnouncementId)
                .orElseThrow(()-> new EntityNotFoundException("공고를 찾을 수 없습니다"));

        member.addToWishlist(jobAnnouncement);

        memberRepository.save(member);
    }

    @Transactional
    public void removeFromWishlist(String accessToken, Long jobAnnouncementId) {
        String email = tokenProvider.validateJwtAndGetUserEmail(accessToken);
        MemberEntity member = memberRepository.findByEmail(email)
                .orElseThrow(()-> new EntityNotFoundException("사용자를 찾을 수 없습니다"));

        JobAnnouncement jobAnnouncement = jobAnnouncementRepository.findById(jobAnnouncementId)
                .orElseThrow(()-> new EntityNotFoundException("공고를 찾을 수 없습니다"));

        member.removeFromWishlist(jobAnnouncement);

        memberRepository.save(member);
    }

    public List<JobAnnouncementDto> getAnnouncementPage(Pageable pageable, String accessToken){
        String email = tokenProvider.validateJwtAndGetUserEmail(accessToken);
        MemberEntity member = memberRepository.findByEmail(email)
                .orElseThrow(()-> new EntityNotFoundException("사용자를 찾을 수 없습니다"));
        Page<JobAnnouncement> announcementList = jobAnnouncementRepository.findAll(pageable);
        List<JobAnnouncementDto> announcementDtos = announcementList.map(JobAnnouncementDto::new).stream()
                .map((JobAnnouncementDto item)-> {
                    return item.checkAddedAnnouncement(member);
                }).collect(Collectors.toList());

        return announcementDtos;
    }

    public List<JobAnnouncementDto> getMembersWishlist(String accessToken, Pageable pageable) {
        String email = tokenProvider.validateJwtAndGetUserEmail(accessToken);
        MemberEntity member = memberRepository.findByEmail(email)
                .orElseThrow(()-> new EntityNotFoundException("사용자를 찾을 수 없습니다"));
        Set<JobAnnouncement> jobAnnouncements = member.getWishlist();
        List<JobAnnouncement> jobAnnouncementList = new ArrayList<>(jobAnnouncements);
        jobAnnouncementList.sort(Comparator.comparing(JobAnnouncement::getRecruitmentPeriod));

        return jobAnnouncements.stream().map(JobAnnouncementDto::new).map((JobAnnouncementDto item)-> {
            return item.checkAddedAnnouncement(member);
        }).collect(Collectors.toList());
    }

    public List<JobAnnouncementDto> getAnnoucementsSortbyAddedWishMembers(String accessToken, Pageable pageable) {
        String email = tokenProvider.validateJwtAndGetUserEmail(accessToken);
        MemberEntity member = memberRepository.findByEmail(email)
                .orElseThrow(()-> new EntityNotFoundException("사용자를 찾을 수 없습니다"));
        Page<JobAnnouncement> announcementList = jobAnnouncementRepository.findAllOrderByMembersCount(pageable);
        List<JobAnnouncementDto> announcementDtos = announcementList.map(JobAnnouncementDto::new).stream()
                .map((JobAnnouncementDto item)-> {
                    return item.checkAddedAnnouncement(member);
                }).collect(Collectors.toList());

        return announcementDtos;
    }

    public void deleteAllAnnouncements(){
        jobAnnouncementRepository.deleteAll();
    }

}
