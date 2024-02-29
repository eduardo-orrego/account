package com.nttdata.account.business.impl;

import com.nttdata.account.business.AccountService;
import com.nttdata.account.business.CustomerService;
import com.nttdata.account.model.account.Account;
import com.nttdata.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerService customerService;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, CustomerService customerService) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;
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
        return validationAccountData(account)
            .flatMap(accountRepository::save);
    }

    private Mono<Account> validationAccountData(Account account){

        String customerId = account.getAccountHolders().get(0).getHolderId();

        return customerService.getCustomerById(customerId)
            .switchIfEmpty(Mono.error(new RuntimeException("No se encontraron datos del titular")))
            .flatMap(customer -> accountRepository.save(account));

    }

}
