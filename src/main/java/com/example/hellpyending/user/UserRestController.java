package com.example.hellpyending.user;

import com.example.hellpyending.config.Util;
import com.example.hellpyending.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}") // 보내는 사람 메일 주소
    private String from;
    @GetMapping("/information/check")
    @ResponseBody
    public String check(Principal principal, String nickname){
        Optional<Users> users_ = userService.findByNickname(nickname);
        if (!users_.isPresent()){
            return "사용 가능한 아이디입니다.";
        }

        return "닉네임이 중복 되었습니다.";
    }
    @GetMapping("/password/find")
    @ResponseBody
    public String pwdFind(String email) throws MessagingException {
        Users users = userService.findByEmail(email);
        if (users == null){
            return "존재하지 않는 이메일입니다.";
        }
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true,"UTF-8");
        mimeMessageHelper.setFrom("ghdtmdvy2@naver.com"); // 보낼 주소
        mimeMessageHelper.setTo(email); // 받을 주소
        mimeMessageHelper.setSubject("임시 비밀번호 안내"); // 제목

        StringBuilder body = new StringBuilder();
        body.append(Util.getTempPassword()); // 내용
        mimeMessageHelper.setText(body.toString(), true);
        javaMailSender.send(mimeMessage);

        return "보내짐";
    }
}
