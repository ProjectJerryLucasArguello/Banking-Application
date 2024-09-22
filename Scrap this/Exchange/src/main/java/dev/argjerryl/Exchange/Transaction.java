package dev.argjerryl.Exchange;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.util.Date;

@Document(collection = "transactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    private String id;
    private String accountId;
    private BigDecimal amount = new BigDecimal("10000.00");
    private String currency;
    private Date transactionDate;
}
