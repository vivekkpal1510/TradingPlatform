package com.trading.TradingPlatform.Service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private JavaMailSender javaMailSender;

    public void sendVarificationMail(String email, String otp) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,"UTF-8");
        String subject = "Verification mail";
        String body = "Your verification code is : " + otp;
        try {
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(body,true);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            throw  new MailSendException(e.getMessage());
        }
    }

}
