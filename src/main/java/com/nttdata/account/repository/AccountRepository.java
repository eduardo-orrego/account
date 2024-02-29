package com.nttdata.account.repository;

import com.nttdata.account.model.account.Account;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AccountRepository extends ReactiveMongoRepository<Account, String> {
    Mono<Account> findByAccountNumber(String accountNumber);

    @Query("{ 'accountHolders.holderId' : ?0 }")
    Flux<Account> findByAccountHolderId(String holderId);
}
