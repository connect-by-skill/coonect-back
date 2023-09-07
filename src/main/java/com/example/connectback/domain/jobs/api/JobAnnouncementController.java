package com.example.connectback.domain.jobs.api;

import com.example.connectback.domain.jobs.dto.JobAnnouncementDto;
import com.example.connectback.domain.jobs.service.JobAnnouncementRepositoryImpl;
import com.example.connectback.domain.jobs.service.JobAnnouncementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/announcement")
@RequiredArgsConstructor
@Slf4j
public class JobAnnouncementController {
    private final JobAnnouncementService jobAnnouncementService;
    private final JobAnnouncementRepositoryImpl jobAnnouncementRepository;

    @GetMapping("/sort/wish")
    public ResponseEntity<List<JobAnnouncementDto>> getJobAnnouncement (@RequestHeader("Authorization")String token, Pageable pageable){
        String accessToken = token.substring(7);
        List<JobAnnouncementDto> jobAnnouncementDtos = jobAnnouncementService.getAnnoucementsSortbyAddedWishMembers(accessToken, pageable);

        return ResponseEntity.ok().body(jobAnnouncementDtos);
    }

    @GetMapping("")
    public ResponseEntity<List<JobAnnouncementDto>> getJobAnnouncementSortbyAddedWishMembers (@RequestHeader("Authorization")String token, Pageable pageable){
        String accessToken = token.substring(7);
        List<JobAnnouncementDto> jobAnnouncementDtos = jobAnnouncementService.getAnnouncementPage(pageable, accessToken);

        return ResponseEntity.ok().body(jobAnnouncementDtos);
    }

    @PostMapping("/wish")
    public ResponseEntity<?> addWishList(@RequestHeader("Authorization")String token, @RequestParam Long jobAnnoucemnetId) {
        String accessToken = token.substring(7);
        jobAnnouncementService.addToWishlist(accessToken, jobAnnoucemnetId);

        return ResponseEntity.ok().body("성공");
    }

    @DeleteMapping("/wish")
    public ResponseEntity<?> cancleWishList(@RequestHeader("Authorization")String token, @RequestParam Long jobAnnoucemnetId) {
        String accessToken = token.substring(7);
        jobAnnouncementService.removeFromWishlist(accessToken, jobAnnoucemnetId);

        return ResponseEntity.ok().body("성공");
    }

    @GetMapping("/wish")
    public ResponseEntity<?> getWishList(@RequestHeader("Authorization")String token, Pageable pageable) {
        String accessToken = token.substring(7);
        List<JobAnnouncementDto> response = jobAnnouncementService.getMembersWishlist(accessToken, pageable);

        return ResponseEntity.ok().body(response);
    }

    // 고용형태, 회사 규모, 경력 요구, 주소, keyword로 검색하고 id와 recruitmentPeriod 둘 중 하나를 기준으로 정렬할 수 있다.
    @GetMapping("/search")
    public ResponseEntity<?> getDynamicJobAnnouncementsWithKeyword(@RequestHeader("Authorization")String token, Pageable pageable,
                                                        @RequestParam(required = false) String typeOfEmployment,
                                                        @RequestParam(required = false) String companyType,
                                                        @RequestParam(required = false) Boolean requiredExperienceExists,
                                                        @RequestParam(required = false) String businessAddress,
                                                        @RequestParam(required = false) String keyword) {
        String accessToken = token.substring(7);
        List<JobAnnouncementDto> response = jobAnnouncementRepository.searchJobAnnouncementsWithKeyword(pageable,
                typeOfEmployment,
                companyType,
                requiredExperienceExists,
                businessAddress,
                keyword).stream().map(JobAnnouncementDto::new).collect(Collectors.toList());

        return ResponseEntity.ok().body(response);
    }
}
