package dev.argjerryl.Exchange;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository  extends MongoRepository<Account, String>{
    Account findByUserId(String userId);

}
