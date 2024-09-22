package dev.argjerryl.Exchange;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
    // Custom query methods if needed
}
