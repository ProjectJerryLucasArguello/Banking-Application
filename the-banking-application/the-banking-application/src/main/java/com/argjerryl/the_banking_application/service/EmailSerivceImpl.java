package com.argjerryl.the_banking_application.service;

import com.argjerryl.the_banking_application.dto.EmailDets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSerivceImpl implements EmailService{

    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String senderEmail;

    @Override
    public void sendEmailAlert(EmailDets emailDets) {
        try{
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(senderEmail);
            simpleMailMessage.setTo(emailDets.getRecipient());
            simpleMailMessage.setText(emailDets.getMessageBody());
            simpleMailMessage.setSubject(emailDets.getSubject());

            javaMailSender.send(simpleMailMessage);
            System.out.println("Mail successfully sent");
        } catch (MailException e) {
            throw new RuntimeException(e);
        }
    }
}
