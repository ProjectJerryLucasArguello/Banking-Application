package com.argjerryl.the_banking_application.service;

import com.argjerryl.the_banking_application.dto.Transactiondto;
import com.argjerryl.the_banking_application.enitity.Transaction;

public interface TransactionService {
    void saveTransaction(Transactiondto transactiondto);
}
