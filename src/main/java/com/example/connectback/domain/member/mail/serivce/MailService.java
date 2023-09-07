package com.example.connectback.domain.member.mail.serivce;

import com.example.connectback.global.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class MailService {

    private final JavaMailSender javaMailSender;
    private final RedisService redisService;

    //인증번호 생성
    private final String certificateNumber = createKey();

    @Value("${spring.mail.username}")
    private String id;

    public MimeMessage createMessage(String to)throws MessagingException, UnsupportedEncodingException {
        log.info("보내는 대상 : "+ to);
        log.info("인증 번호 : " + certificateNumber);
        MimeMessage  message = javaMailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, to); // to 보내는 대상
        message.setSubject("WHALE 회원가입 인증 코드: "); //메일 제목

        // 메일 내용 메일의 subtype을 html로 지정하여 html문법 사용 가능
        String msg = "<!DOCTYPE html>\n";
        msg += "<html>\n";
        msg += "<head>\n";
        msg += "    <meta charset=\"UTF-8\">\n";
        msg += "    <title>이메일 주소 확인</title>\n";
        msg += "</head>\n";
        msg += "<body style=\"background-color: #F0F0F0; margin: 0; padding: 0;\">\n";
        msg += "    <div style=\"background-color: white; width: 600px; margin: 0 auto; padding: 5px; border-radius: 10px; border: 10px solid #0099cc; text-align: center;\">\n";
        msg += "        <img src='https://i.postimg.cc/wTW6WncJ/1.png' style=\"max-width: 50%;\" alt='whale-logo'/>\n";
        msg += "        <h1 style=\"font-size: 30px;\">이메일 주소 확인</h1>\n";
        msg += "        <p style=\"font-size: 20px; padding: 15px; animation: textAnimation 2s infinite;\">회원가입이 거의 완료되었습니다!</p>\n";
        msg += "        <style>\n";
        msg += "            @keyframes textAnimation {\n";
        msg += "                0% {\n";
        msg += "                    font-size: 20px;\n";
        msg += "                }\n";
        msg += "                50% {\n";
        msg += "                    font-size: 23px;\n";
        msg += "                }\n";
        msg += "                100% {\n";
        msg += "                    font-size: 20px;\n";
        msg += "                }\n";
        msg += "            }\n";
        msg += "        </style>\n";
        msg += "        <p style=\"font-size: 17px; font-weight: bold;\">아래 확인 코드를 회원가입 화면에서 입력해주세요.</p>\n";
        msg += "            <table style=\"border-collapse: collapse; border: 0; background-color: #F4F4F4; height: 70px; width: 100%; max-width: 400px; margin: 0 auto; border-radius: 6px;\">\n";
        msg += "                <tbody>\n";
        msg += "                    <tr>\n";
        msg += "                        <td style=\"text-align: center; vertical-align: middle; font-size: 30px;\">";
        msg += certificateNumber; // 변수를 문자열로 추가
        msg += "                        </td>\n";
        msg += "                    </tr>\n";
        msg += "                </tbody>\n";
        msg += "            </table>\n";
        msg += "        <img src=\"https://i.postimg.cc/c4pgRZPD/moving-whale-rotation.gif\" alt=\"이미지 설명\" style=\"max-width: 50%; display: block; margin: 0 auto;\">\n";
        msg += "    </div>\n";
        msg += "</body>\n";
        msg += "</html>";

        message.setText(msg, "utf-8", "html"); //내용, charset타입, subtype
        message.setFrom(new InternetAddress(id,"WHALE 가입 안내")); //보내는 사람의 메일 주소, 보내는 사람 이름

        return message;
    }

    // 인증코드 만들기
    public static String createKey() {
        // UUID.randomUUID()를 사용하여 고유한 문자열 생성
        String key = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6);
        return key;
    }

    /*
        메일 발송
        sendSimpleMessage의 매개변수로 들어온 to는 인증번호를 받을 메일주소
        MimeMessage 객체 안에 내가 전송할 메일의 내용을 담아준다.
        bean으로 등록해둔 javaMailSender 객체를 사용하여 이메일 send
     */
    public String sendSimpleMessage(String to)throws Exception {
        MimeMessage message = createMessage(to);
        try{
            redisService.setDataExpire(certificateNumber, to, 60 * 10L); // 유효시간 10분
            javaMailSender.send(message); // 메일 발송
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return certificateNumber; // 메일로 보냈던 인증 코드를 서버로 리턴
    }

    public String verifyCode(String code) throws ChangeSetPersister.NotFoundException {
        String memberEmail = redisService.getData(code);
        if(memberEmail == null) {
            throw new ChangeSetPersister.NotFoundException();
        }
        redisService.deleteData(code);

        return certificateNumber;
    }
}
