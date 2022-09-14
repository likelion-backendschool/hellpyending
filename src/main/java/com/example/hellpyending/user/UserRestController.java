package com.example.hellpyending.user;

import com.example.hellpyending.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.security.Principal;
import java.util.Optional;

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
    public String pwdFind() throws MessagingException {
        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage,true,"UTF-8");
        mimeMessageHelper.setFrom("ghdtmdvy2@naver.com");
        mimeMessageHelper.setTo("dhfl0909@naver.com");
    }
}
