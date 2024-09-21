package com.argjerryl.the_banking_application.service;

import com.argjerryl.the_banking_application.enitity.Transaction;
import com.argjerryl.the_banking_application.repository.TransactionRepository;
import lombok.AllArgsConstructor;
//import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
@Component
@AllArgsConstructor
public class BankStatement {

    private TransactionRepository transactionRepository;

    /**
     * Create a list to store the tranactions with the data which they had occured
     * Should list the account number
     * SHould generate the pdf file of the information above
     * send via email
     */

    //Checking the accountNumber and it's action between a certain time frame
    public List<Transaction> generateStatement(String accountNumber, String startDate, String endDate){
        //parse() takes a string and manipulates it into the type of variable the creator wants
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);
        return transactionRepository.findAll().stream().filter(transaction -> transaction.getAccountNumber().equals(accountNumber))
                .filter(transaction -> transaction.getCreatedAt().isEqual(start))
                .filter(transaction -> transaction.getCreatedAt().isEqual(end)).toList();

    }

}
