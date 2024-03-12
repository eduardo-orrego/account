package com.nttdata.account.business;

import com.nttdata.account.api.request.AccountRequest;
import com.nttdata.account.model.account.Account;
import java.math.BigInteger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {

    Mono<Account> saveAccount(AccountRequest account);

    Mono<Account> updateAccount(AccountRequest account, String accountId);

    Mono<Account> getAccountByAccountNumber(BigInteger accountNumber);

    Flux<Account> getAccountsByCustomerId(String customerId);

    Mono<Void> deleteAccount(String accountId);

}
