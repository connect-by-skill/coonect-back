package com.example.connectback.domain.member.mail.api;

import com.example.connectback.domain.member.mail.serivce.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MailController {

    private final MailService mailService;

    @PostMapping("mail/send")
    public String mailConfirm(@RequestParam String email) throws Exception {
        String code = mailService.sendSimpleMessage(email);
        log.debug("인증코드 : " + code);
        return code;
    }

    @GetMapping("mail/check")
    public ResponseEntity<?> compareCode(@RequestParam String code) throws ChangeSetPersister.NotFoundException {

        mailService.verifyCode(code);

        return ResponseEntity.ok().body("인증 성공");
    }
}
