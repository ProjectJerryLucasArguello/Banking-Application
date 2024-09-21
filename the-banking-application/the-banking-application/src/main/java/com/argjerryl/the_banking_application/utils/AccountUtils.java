package com.argjerryl.the_banking_application.utils;

import java.time.Year;

public class AccountUtils {
    public static final String ACCOUNT_EXISTS_CODE = "001";
    public static final String ACCOUNT_EXISTS_MESSAGE = "THIS USER ACCOUNT ALREADY EXISTS";
    //-----------------------------------------
    public static final String ACCOUNT_CREATION_SUCCESS = "002";
    public static final String ACCOUNT_CREATION_MESSAGE = "ACCOUNT HAS BEEN SUCCESSFULLY CREATED";
    //---------------------------------------
    public static final String ACCOUNT_NOT_EXIST_CODE = "003";
    public static final String ACCOUNT_NOT_EXIST_MESSAGE = "USer with the provided information does not exist!";
    //-------------------------------------
    public static final String ACCOUNT_CODE_FOUND = "004";
    public static final String ACCOUNT_FOUND_SUCCESS = "User Account Found";
    //---------------------------------------
    public static final String ACCOUNT_CREDITED_SUCCESS = "005";
    public static final String ACCOUNT_CREDITED_SUCCESS_MESSAGE= "Success! Amount has been sent to user!";
    /*-----------------------------------------------*/
    public static final String INSUFFICIENT_BALANCE_CODE = "006";
    public static final String INSUFFICIENT_BALANCE_MESSAGE = "Insufficient funds";
    /*-----------------------------------------------*/
    public static final String ACCOUNT_DEBITED_SUCCESS = "007";
    public static final String ACCOUNT_DEBITED_MESSAGE = "Account has been successfully debited";
    /*-----------------------------------------------*/
    public static final String ACCOUNT_TRANSFER_SUCCESS = "008";
    public static final String ACCOUNT_TRANSFER_MESSAGE = "Your payment transfer has been completed";

    public static String generateAccountNumber(){
        Year currentYear = Year.now();

        int min = 100000;
        int max = 999999;

        int randomNum = (int)Math.floor(Math.random()*(max-min + 1)+min);

        String year = String.valueOf(currentYear);
        String randomNumber = String.valueOf(randomNum);
        StringBuilder accountNumber = new StringBuilder();
        return accountNumber.append(year).append(randomNumber).toString();
    }
}
