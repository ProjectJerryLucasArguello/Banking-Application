package com.argjerryl.the_banking_application.service;

import com.argjerryl.the_banking_application.dto.*;
import com.argjerryl.the_banking_application.enitity.AccountInfo;
import com.argjerryl.the_banking_application.repository.AccountRepository;
import com.argjerryl.the_banking_application.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;

@Service

public class UserServiceImpl implements UserService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    TransactionService transactionService;

    @Override
    public BankResponse createAccount(AccountCommand accountCommand) {
        /*
        * 1.Create A user account by saving it into the db
        * */

        if(accountRepository.existsByEmail(accountCommand.getEmail())){
           return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                    .accountResponse(null)
                    .build();
        }
        AccountInfo newUser = AccountInfo.builder()
                .firstName(accountCommand.getFirstName())
                .lastName(accountCommand.getLastName())
                .gender(accountCommand.getGender())
                .address(accountCommand.getAddress())
                .Residence(accountCommand.getResidence())
                .accountNumber(AccountUtils.generateAccountNumber())
                .accountBalance(BigDecimal.ZERO)
                .email(accountCommand.getEmail())
                .contactInfo(accountCommand.getContactInfo())
                .status("ACTIVE")
                .build();

        AccountInfo savedUser = accountRepository.save(newUser);
        //Send email Alert
        EmailDets emailDets = EmailDets.builder()
                .recipient(savedUser.getEmail())
                .subject("ACCOUNT CREATION")
                .messageBody("Success! Your account has been created.\n Your account details: \n" + "Account Name: "+ savedUser.getFirstName() + " "+ savedUser.getLastName()+ "\nAccount Number: " + savedUser.getAccountNumber())
                .build();
        emailService.sendEmailAlert(emailDets);

        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                .accountResponse(AccountResponse.builder()
                        .accountBalance(savedUser.getAccountBalance())
                        .accountNumber(savedUser.getAccountNumber())
                        .accountName(savedUser.getFirstName() + " " + savedUser.getLastName() )
                        .build())
                .build();
    }

    @Override
    public String nameEnquiry(EnquiryRequest enquiryRequest) {
        boolean isAccountExist = accountRepository.existsByAccountNumber(enquiryRequest.getAccountNumber());
        if(!isAccountExist){
            return AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE;
        }
        AccountInfo foundUser = accountRepository.findByAccountNumber(enquiryRequest.getAccountNumber());
        return foundUser.getFirstName()+ " " + foundUser.getLastName();
    }

    @Override
    public BankResponse balanceEnquiry(EnquiryRequest enquiryRequest) {
        //check provided account exists in database
        boolean isAccountExist = accountRepository.existsByAccountNumber(enquiryRequest.getAccountNumber());
        if(!isAccountExist){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountResponse(null)
                    .build();
        }
        //Retrieve object of user
        AccountInfo foundUser =accountRepository.findByAccountNumber(enquiryRequest.getAccountNumber());
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CODE_FOUND)
                .responseMessage(AccountUtils.ACCOUNT_FOUND_SUCCESS)
                .accountResponse(AccountResponse.builder()
                        .accountBalance(foundUser.getAccountBalance())
                        .accountNumber(foundUser.getAccountNumber())
                        .accountName(foundUser.getFirstName()+ " " + foundUser.getLastName())
                        .build())
                .build();
    }

    @Override
    public BankResponse creditAccount(DebitCreditRequest request) {
        //Confirm whether the account exists
        boolean isAccountExist = accountRepository.existsByAccountNumber(request.getAccountNumber());
        if (!isAccountExist){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountResponse(null)
                    .build();
        }
       AccountInfo userToCredit = accountRepository.findByAccountNumber(request.getAccountNumber());
        //Update information of user
        userToCredit.setAccountBalance(userToCredit.getAccountBalance().add(request.getAmount()));
        //Save information in repository
        accountRepository.save(userToCredit);

        //Save transaction
        Transactiondto transactionDto = Transactiondto.builder()
                .accountNumber(userToCredit.getAccountNumber())
                .transactionType("CREDIT")
                .amount(request.getAmount())
                .build();

        transactionService.saveTransaction(transactionDto);

        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREDITED_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_CREDITED_SUCCESS_MESSAGE)
                .accountResponse(AccountResponse.builder()
                        .accountName(userToCredit.getFirstName() + " " + userToCredit.getLastName())
                        .accountBalance(userToCredit.getAccountBalance())
                        .accountNumber(userToCredit.getAccountNumber())
                        .build())
                .build();

    }

    @Override
    public BankResponse debitAccount(DebitCreditRequest request) {
        //Checking if account exists
        boolean isAccountExist = accountRepository.existsByAccountNumber(request.getAccountNumber());
        if (!isAccountExist){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountResponse(null)
                    .build();
        }
        //Checking if the amount you intend to withdraw is not more than what has been requested
        AccountInfo userToDebit = accountRepository.findByAccountNumber(request.getAccountNumber());

        //BigDecimal --> String --> Int
        /*int availableBalance = Integer.parseInt(userToDebit.getAccountBalance().toString());
        int debitAmount = Integer.parseInt(request.getAmount().toString());*/

        /* "timestamp": "2024-09-05T00:03:56.690+00:00",
        "status": 500,
        "error": "Internal Server Error",
        "path": "/api/user/debit"*/

        BigInteger availableBalance = userToDebit.getAccountBalance().toBigInteger();
        BigInteger debitAmount = request.getAmount().toBigInteger();
        if(availableBalance.intValue() < debitAmount.intValue())
        {
            return BankResponse.builder()
                    .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                    .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                    .accountResponse(null)
                    .build();
        }else{
            userToDebit.setAccountBalance(userToDebit.getAccountBalance().subtract(request.getAmount()));
            accountRepository.save(userToDebit);
            //---------------------------
            Transactiondto transactionDto = Transactiondto.builder()
                    .accountNumber(userToDebit.getAccountNumber())
                    .transactionType("DEBIT")
                    .amount(request.getAmount())
                    .build();
            /*Retrieve the accountNumber of the user that had which requested
            *
            *
            * */
            transactionService.saveTransaction(transactionDto);

            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_DEBITED_SUCCESS)
                    .responseMessage(AccountUtils.ACCOUNT_DEBITED_MESSAGE)
                    .accountResponse(AccountResponse.builder()
                            .accountNumber(request.getAccountNumber())
                            .accountName(userToDebit.getFirstName() + " " + userToDebit.getLastName())
                            .accountBalance(userToDebit.getAccountBalance())
                            .build())
                    .build();
        }
    }

    @Override
    public BankResponse transferAccount(TransferRequest transferRequest) {
       //get the account by debit(1. Checks if the account exists in the first place)
        boolean doesFromAccountNumberExist = accountRepository.existsByAccountNumber(transferRequest.getFromAccountNumber());
        //boolean doesToAccountNumberExist= accountRepository.existsByAccountNumber(transferRequest.getFromAccountNumber());
        if(!doesFromAccountNumberExist){
            BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountResponse(null)
                    .build();
        }

        AccountInfo fromAccountUser = accountRepository.findByAccountNumber(transferRequest.getFromAccountNumber());
        if(transferRequest.getAmount().compareTo(fromAccountUser.getAccountBalance()) > 0){
            return BankResponse.builder()
                    .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                    .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                    .accountResponse(null)
                    .build();
        }
        //check if the amount is not more than the current balance
        fromAccountUser.setAccountBalance(fromAccountUser.getAccountBalance().subtract(transferRequest.getAmount()));
        String sourceUsername = fromAccountUser.getFirstName() + " " + fromAccountUser.getLastName();
        accountRepository.save(fromAccountUser);
        EmailDets debitAlert =EmailDets.builder()
                .subject("EMAIL ALERT: DEBIT")
                .recipient(fromAccountUser.getEmail())
                .messageBody("The sum of "+ transferRequest.getAmount() + " has been deducted from your account! Your current balance is " + fromAccountUser.getAccountBalance())
                .build();
        emailService.sendEmailAlert(debitAlert);

        AccountInfo destinationAccountUser = accountRepository.findByAccountNumber(transferRequest.getToAccountNumber());
        destinationAccountUser.setAccountBalance(destinationAccountUser.getAccountBalance().add(transferRequest.getAmount()));
        //String recipientUsername = destinationAccountUser.getFirstName()+ " " + destinationAccountUser.getLastName();//
        accountRepository.save(destinationAccountUser);
        EmailDets creditAlert =EmailDets.builder()
                .subject("EMAIL ALERT: CREDIT")
                .recipient(fromAccountUser.getEmail())
                .messageBody("The sum of "+ transferRequest.getAmount() + " has been sent to your account from " + sourceUsername)
                .build();
        emailService.sendEmailAlert(creditAlert);

        Transactiondto transactionDto = Transactiondto.builder()
                .accountNumber(destinationAccountUser.getAccountNumber())
                .transactionType("TRANSFER")
                .amount(transferRequest.getAmount())
                .build();

        transactionService.saveTransaction(transactionDto);


        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_TRANSFER_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_TRANSFER_MESSAGE)
                .accountResponse(null)
                .build();

       //debit the account
       //get the account to credit
       //credit the account
    }


}
