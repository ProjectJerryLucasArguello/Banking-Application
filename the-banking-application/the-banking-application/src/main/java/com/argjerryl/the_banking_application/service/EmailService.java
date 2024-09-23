package com.argjerryl.the_banking_application.service;

import com.argjerryl.the_banking_application.dto.EmailDets;

public interface EmailService {
    void sendEmailAlert(EmailDets emailDets);
    void sendEmailDetailsWithAttachment(EmailDets emailDets);
}
