package com.nttdata.account.business.impl;

import com.nttdata.account.business.AccountService;
import com.nttdata.account.model.Account;
import com.nttdata.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Mono<Account> getAccountByAccountNumber(String accountId) {
        return accountRepository.findByAccountNumber(accountId);
    }

    @Override
    public Flux<Account> getAccountsByHolderId(String holderId) {

        return accountRepository.findByAccountHolderId(holderId);
    }

    @Override
    public Mono<Account> saveAccount(Account account) {
        return accountRepository.save(account);
    }

}
