package com.argjerryl.the_banking_application.service;

import com.argjerryl.the_banking_application.dto.EmailDets;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Objects;

@Service
@Slf4j
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

    @Override
    public void sendEmailDetailsWithAttachment(EmailDets emailDets) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        try{
            mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
            mimeMessageHelper.setFrom(senderEmail);
            mimeMessageHelper.setTo(emailDets.getRecipient());
            mimeMessageHelper.setText(emailDets.getMessageBody());
            mimeMessageHelper.setSubject(emailDets.getSubject());
            mimeMessageHelper.setSubject(emailDets.getSubject());

            FileSystemResource file = new FileSystemResource(new File(emailDets.getAttachement()));
            mimeMessageHelper.addAttachment(Objects.requireNonNull(file.getFilename()), file);
            javaMailSender.send(mimeMessage);

            log.info("{} has been sent to user with email {}", file.getFilename(), emailDets.getRecipient());

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
