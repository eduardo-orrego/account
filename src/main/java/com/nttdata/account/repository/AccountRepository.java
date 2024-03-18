package com.nttdata.account.repository;

import com.nttdata.account.model.account.Account;
import java.math.BigInteger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountRepository {

    Mono<Account> findAccount(String accountId);
    Mono<Account> findAccount(BigInteger accountNumber);
    Flux<Account> findAccounts(BigInteger customerDocument);
    Mono<Account> saveAccount(Account account);

    Mono<Boolean> findExistsAccount(String type, BigInteger customerDocument);

    Mono<Boolean> findExistsAccount(BigInteger accountNumber);


}
