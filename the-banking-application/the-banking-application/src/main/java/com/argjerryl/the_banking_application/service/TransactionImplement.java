package com.argjerryl.the_banking_application.service;

import com.argjerryl.the_banking_application.dto.Transactiondto;
import com.argjerryl.the_banking_application.enitity.Transaction;
import com.argjerryl.the_banking_application.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionImplement implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    /*
    *   [
        {
        "transactionId": "de89c4a1-38e2-4cbf-bbad-2f3c941abddd",
        "transactionType": "TRANSFER",
        "amount": 1800.05,
        "accountNumber": "2024707653",
        "status": "SUCCESS",
        "createdAt": "2024-09-21",
        "modifiedAt": "2024-09-21"
         }
        ]
    *
    * */
    @Override
    public void saveTransaction(Transactiondto transactiondto) {
        Transaction transaction = Transaction.builder()
                .transactionType(transactiondto.getTransactionType())
                .accountNumber(transactiondto.getAccountNumber())
                .amount(transactiondto.getAmount())
                .status("SUCCESS")
                .build();
        transactionRepository.save(transaction);
        System.out.println("Transaction saved successful");
    }
}
