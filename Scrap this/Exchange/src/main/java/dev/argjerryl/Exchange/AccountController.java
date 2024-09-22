package dev.argjerryl.Exchange;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
@RestController

@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/create")
    public Account createAccount(@RequestParam String userId, @RequestParam String currency) {
        return accountService.createAccount(userId, currency);
    }

    @PostMapping("/withdraw")
    public BigDecimal withdraw(@RequestParam String userId, @RequestParam BigDecimal amount, @RequestParam String currency) throws IOException {
        return accountService.withdraw(userId, amount, currency);
    }

    @PostMapping("/deposit")
    public void deposit(@RequestParam String userId, @RequestParam BigDecimal amount, @RequestParam String currency) throws IOException {
        accountService.deposit(userId, amount, currency);
    }

    @GetMapping("/balance")
    public BigDecimal getBalance(@RequestParam String userId) {
        Account account = accountService.findByUserId(userId);
        return account.getBalance(); // This should work if Account has a getBalance() method
    }
    @GetMapping("/balance-in-currency")
    public BigDecimal getBalanceInCurrency(@RequestParam String userId, @RequestParam String targetCurrency) {
        return accountService.getBalanceInCurrency(userId, targetCurrency);
    }



}
