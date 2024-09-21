package com.argjerryl.the_banking_application.controller;

import com.argjerryl.the_banking_application.dto.*;
import com.argjerryl.the_banking_application.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User Account Management APIs")
public class AccountController {

    @Autowired
    UserService userService;
@Operation(
        summary = "Create New User Account",
        description = "Creating a new user and assigning an account ID"
)
@ApiResponse(
        responseCode = "201",
        description = "Http Status 201 CREATED"
)
    @PostMapping
    public BankResponse createAccount(@RequestBody AccountCommand accountCommand){
        return userService.createAccount(accountCommand);
    }

    @Operation(
            summary = "Balance Enquiry",
            description = "Given an account number, check how much the user has"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping("balanceEnquiry")
    public BankResponse balanceEnquiry(@RequestBody EnquiryRequest enquiryRequest){
        return userService.balanceEnquiry(enquiryRequest);
    }

    @GetMapping("nameEnquiry")
    public String nameEnquiry(@RequestBody EnquiryRequest enquiryRequest){
      return userService.nameEnquiry(enquiryRequest);
    }

    @PostMapping("credit")
    public BankResponse creditAccount(@RequestBody DebitCreditRequest request){
        return userService.creditAccount(request);
    }

    @PostMapping("debit")
    public BankResponse debitAccount(@RequestBody DebitCreditRequest request){
        return userService.debitAccount(request);
    }

    @PostMapping("transfer")
    public BankResponse transferAccount(@RequestBody TransferRequest transferRequest){
        return userService.transferAccount(transferRequest);
    }

}

