package com.nttdata.account.business;

import com.nttdata.account.model.account.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {
    Mono<Account> getAccountByAccountNumber(String accountNumber);

    Flux<Account> getAccountsByHolderId(String holderId);

    Mono<Account> saveAccount(Account account);
}
