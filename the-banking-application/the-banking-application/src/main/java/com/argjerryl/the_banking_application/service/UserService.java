package com.argjerryl.the_banking_application.service;

import com.argjerryl.the_banking_application.dto.*;

public interface UserService {

    BankResponse createAccount(AccountCommand accountCommand);
    //We need to call on the account on how much we transfer from one to another.
    String nameEnquiry(EnquiryRequest enquiryRequest);

    BankResponse balanceEnquiry(EnquiryRequest enquiryRequest);
    //BankResponse ConvertCurrency (CurrencyConversionService conversionService);

    BankResponse creditAccount(DebitCreditRequest request);

    BankResponse debitAccount(DebitCreditRequest request);

    BankResponse transferAccount(TransferRequest transferRequest);
}
