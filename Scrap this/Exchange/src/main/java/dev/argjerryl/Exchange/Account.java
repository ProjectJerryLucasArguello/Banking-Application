package dev.argjerryl.Exchange;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;

@Document(collection = "accounts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    private String id;

    @Indexed(unique = true)
    private String userId;

    private BigDecimal balance = new BigDecimal("10000.00");
    private String currency;


    // No need to manually define setters and getters unless custom logic is required
    // Lombok's @Data annotation already generates them
}




