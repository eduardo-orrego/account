package com.nttdata.account.repository;

import com.nttdata.account.model.account.Account;
import java.math.BigInteger;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AccountReactiveMongodb extends ReactiveMongoRepository<Account, String> {

    Mono<Account> findByAccountNumber(BigInteger accountNumber);

    Flux<Account> findByAccountHoldersCustomerDocument(BigInteger customerDocument);

    Mono<Boolean> existsByTypeAndAccountHoldersCustomerDocument(String type, BigInteger customerDocument);

    Mono<Boolean> existsByAccountNumber(BigInteger accountNumber);

}
