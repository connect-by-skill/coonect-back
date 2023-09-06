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

    /*
    * 요구학력, 입사형태, 기업형태, 요구경력 유무, 사업장 주소에 대한 조건으로 검색할 수 있다.
    * */
    @GetMapping("/search")
    public ResponseEntity<?> getDynamicJobAnnouncements(@RequestHeader("Authorization")String token, Pageable pageable,
                                                        @RequestParam String requiredEducation,
                                                        @RequestParam String entryForm,
                                                        @RequestParam String companyType,
                                                        @RequestParam Boolean requiredExperienceExists,
                                                        @RequestParam String businessAddress) {
        String accessToken = token.substring(7);
        List<JobAnnouncementDto> response = (List<JobAnnouncementDto>) jobAnnouncementRepository.searchJobAnnouncements(requiredEducation,
                entryForm,
                companyType,
                requiredExperienceExists,
                businessAddress).stream().map(JobAnnouncementDto::new);

        return ResponseEntity.ok().body(response);
    }
}
