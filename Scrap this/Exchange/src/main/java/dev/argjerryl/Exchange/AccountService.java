package dev.argjerryl.Exchange;

import dev.argjerryl.Exchange.Exception.InsufficientFundsException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CurrencyConversionService currencyConversionService;

    public BigDecimal withdraw(String userId, BigDecimal amount, String targetCurrency) throws IOException {
        Account account = accountRepository.findByUserId(userId);
        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient funds");
        }


        BigDecimal convertedAmount = currencyConversionService.convertCurrency(amount, account.getCurrency(), targetCurrency);

        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAccountId(String.valueOf(account.getId()));
        transaction.setAmount(convertedAmount);
        transaction.setCurrency(targetCurrency);
        transaction.setTransactionDate(new Date());
        transactionRepository.save(transaction);

        return convertedAmount;
    }

    public void deposit(String userId, BigDecimal amount, String sourceCurrency) throws IOException {
        Account account = accountRepository.findByUserId(userId);

        BigDecimal convertedAmount = currencyConversionService.convertCurrency(amount, sourceCurrency, account.getCurrency());

        account.setBalance(account.getBalance().add(convertedAmount));
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAccountId(String.valueOf(account.getId()));
        transaction.setAmount(convertedAmount);
        transaction.setCurrency(account.getCurrency());
        transaction.setTransactionDate(new Date());
        transactionRepository.save(transaction);
    }

    public BigDecimal getBalanceInCurrency(String userId, String targetCurrency) {
        Account account = accountRepository.findByUserId(userId);

        if (account == null) {
            throw new RuntimeException("Account not found for user: " + userId);
        }

        String accountCurrency = account.getCurrency();
        BigDecimal balance = account.getBalance();

        if (accountCurrency.equals(targetCurrency)) {
            return balance;
        }

        try {
            // Directly return the converted balance
            return currencyConversionService.convertCurrency(balance, accountCurrency, targetCurrency);
        } catch (IOException e) {
            throw new RuntimeException("Currency conversion failed: " + e.getMessage(), e);
        }
    }

    public Account createAccount(String userId, String currency) {
        Account account = new Account();
        account.setUserId(userId);
        account.setCurrency(currency);
        account.setBalance(new BigDecimal("10000.00")); // Set default balance
        return accountRepository.save(account);
    }



    public Account findByUserId(String userId) {
        return accountRepository.findByUserId(userId);
    }
}
