package com.argjerryl.the_banking_application.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailDets {
    private String recipient;
    private String messageBody;
    private String subject;
    private String attachement;
}
