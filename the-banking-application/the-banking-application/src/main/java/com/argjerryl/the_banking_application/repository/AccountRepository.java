package com.argjerryl.the_banking_application.repository;

import com.argjerryl.the_banking_application.enitity.AccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountInfo, Long> {
    Boolean existsByEmail(String email);

    Boolean existsByAccountNumber(String accountNumber);

    AccountInfo findByAccountNumber(String accountNumber);

}
