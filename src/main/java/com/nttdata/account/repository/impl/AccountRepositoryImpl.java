package com.nttdata.account.repository.impl;

import com.nttdata.account.model.account.Account;
import com.nttdata.account.repository.AccountReactiveMongodb;
import com.nttdata.account.repository.AccountRepository;
import java.math.BigInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
public class AccountRepositoryImpl implements AccountRepository {

    @Autowired
    private AccountReactiveMongodb accountReactiveMongodb;

    @Override
    public Mono<Account> findAccount(BigInteger accountNumber) {
        return accountReactiveMongodb.findByAccountNumber(accountNumber)
            .doOnSuccess(accountEntity -> log.info(
                "Successful find - accountNumber: ".concat(accountNumber.toString())));
    }

    @Override
    public Flux<Account> findAccounts(String holderId) {
        return accountReactiveMongodb.findByAccountHoldersHolderId(holderId)
            .doOnComplete(() -> log.info("Successful find - holderId: ".concat(holderId)));
    }

    @Override
    public Mono<Account> saveAccount(Account account) {
        return accountReactiveMongodb.save(account)
            .doOnSuccess(accountEntity -> log.info("Successful save - accountId: ".concat(accountEntity.getId())));
    }

    @Override
    public Mono<Boolean> findExistsAccount(String type, String holderId) {
        return accountReactiveMongodb.existsByTypeAndAccountHoldersHolderId(type, holderId)
            .doOnSuccess(exists -> log.info("Successful exists - accountId: ".concat(holderId)));
    }

    @Override
    public Mono<Boolean> findExistsAccount(String accountId) {
        return accountReactiveMongodb.existsById(accountId)
            .doOnSuccess(exists -> log.info("Successful exists - accountId: ".concat(accountId)));
    }
}
