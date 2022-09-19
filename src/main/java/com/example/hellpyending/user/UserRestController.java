package com.example.hellpyending.user;

import com.example.hellpyending.config.Util;
import com.example.hellpyending.user.entity.EmailCertificateKey;
import com.example.hellpyending.user.entity.Users;
import com.example.hellpyending.user.redis.EmailRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

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

    private final EmailRedisRepository emailRedisRepository;

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
    public String pwdFind(String email,String username) throws MessagingException {
        Optional<Users> users_= userService.findByEmailAndUsername(email,username);
        if (!users_.isPresent()){
            return "정보가 일치하지 않습니다.";
        }

        Users users = users_.get();
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true,"UTF-8");
        mimeMessageHelper.setFrom(from); // 보낼 주소
        mimeMessageHelper.setTo(email); // 받을 주소
        mimeMessageHelper.setSubject("헬피엔딩 계정 비밀번호 찾기"); // 제목

        StringBuilder body = new StringBuilder();
        String CertificateKey = Util.getTempPassword(); // 인증키 난수 발생
        EmailCertificateKey emailCertificateKey = new EmailCertificateKey(users.getUsername(),CertificateKey); // 인증키 생성
        emailRedisRepository.save(emailCertificateKey); // Redis key 값 저장.
        body.append(CertificateKey); // 내용
        mimeMessageHelper.setText(body.toString(), true);
        javaMailSender.send(mimeMessage);

        return null;
    }

    @GetMapping("/password/check")
    public String pwdChange(String email,String username,String certificateKey) {
        Optional<Users> users_= userService.findByEmailAndUsername(email,username);

        if (!users_.isPresent()){
            return "인증에 실패하였습니다.";
        }
        Users users = users_.get();

        Optional<EmailCertificateKey> emailCK_ = emailRedisRepository.findById(users.getUsername()); // key 값 저장 된 것을 가져오기.
        EmailCertificateKey emailCK = emailCK_.get(); // 있는 지 체크.
        if (emailCK.getCertificateKey().equals(certificateKey)){
            return "인증이 되었습니다.";
        }
        return "인증에 실패하였습니다.";
    }
}
