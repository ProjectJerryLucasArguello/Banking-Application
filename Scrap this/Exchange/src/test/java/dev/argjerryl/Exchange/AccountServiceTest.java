package dev.argjerryl.Exchange;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class AccountServiceTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Test
    public void testCreateAccount() {
        Account account = accountService.createAccount("user123", "USD");

        // Verify the account was created
        assertNotNull(account);
        assertEquals("user123", account.getUserId());
        assertEquals(new BigDecimal("10000.00"), account.getBalance()); // Default balance
        assertEquals("USD", account.getCurrency());

        // Verify the account was saved in the database
        Account savedAccount = accountRepository.findByUserId("user123");
        assertNotNull(savedAccount);
        assertEquals(new BigDecimal("10000.00"), savedAccount.getBalance());
    }
}

