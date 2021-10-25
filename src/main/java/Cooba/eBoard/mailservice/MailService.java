package Cooba.eBoard.mailservice;

import Cooba.eBoard.WebUser.WebUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private WebUserRepository webUserRepository;
    @Value("${spring.mail.username}")
    String ServerEmail;
    @Value("${web.formal-url}")
    String FormalUrl;

    public void sendConfirmMail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(ServerEmail);
        message.setTo(email);
        message.setSubject("這是 eBoard 認證信");
        message.setText("http://" + FormalUrl + "/register/" + webUserRepository.findByEmail(email).get().getId());

        mailSender.send(message);
    }
}


