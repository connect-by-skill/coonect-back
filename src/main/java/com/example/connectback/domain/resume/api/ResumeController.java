package com.example.connectback.domain.resume.api;

import com.example.connectback.domain.resume.dto.ResumeDto;
import com.example.connectback.domain.resume.dto.ResumeRequest;
import com.example.connectback.domain.resume.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resumes")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;

    @PostMapping
    public ResponseEntity<ResumeDto> writeResume(@RequestHeader("Authorization") String token,
                                                 @RequestBody ResumeRequest resumeRequest) {
        String accessToken = token.substring(7);
        ResumeDto response = resumeService.createResume(accessToken, resumeRequest);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<?> getResume(@RequestHeader("Authorization") String token) {
        String accessToken = token.substring(7);
        ResumeDto response = resumeService.getResume(accessToken);

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> removeResume(@RequestHeader("Authorization") String token) {
        String accessToken = token.substring(7);
        resumeService.deleteResume(accessToken);

        return ResponseEntity.ok().body("삭제성공");
    }
}
