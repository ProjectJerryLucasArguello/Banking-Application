package com.argjerryl.the_banking_application.repository;

import com.argjerryl.the_banking_application.enitity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,String> {

}
