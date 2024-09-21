package com.argjerryl.the_banking_application.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountCommand {
    private String firstName;
    private String lastName;
    private String gender;
    private String address;
    private String Residence;
    private String email;
    private String contactInfo;
}
