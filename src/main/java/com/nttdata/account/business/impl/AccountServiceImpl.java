package com.nttdata.account.business.impl;

import com.nttdata.account.business.AccountService;
import com.nttdata.account.business.CustomerService;
import com.nttdata.account.model.account.Account;
import com.nttdata.account.model.account.AccountHolder;
import com.nttdata.account.model.enums.AccountTypeEnum;
import com.nttdata.account.model.enums.CustomerTypeEnum;
import com.nttdata.account.model.enums.HolderTypeEnum;
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

        return accountRepository.findByAccountHoldersHolderId(holderId);
    }

    @Override
    public Mono<Account> saveAccount(Account account) {
        return validationData(account)
            .flatMap(accountRepository::save);
    }

    private Mono<Account> validationData(Account account) {

        String customerId = account.getAccountHolders().stream()
            .filter(accountHolder -> accountHolder.getHolderType().name().equals(HolderTypeEnum.PRIMARY.name()))
            .findFirst().map(AccountHolder::getHolderId).orElse("");

        String accountType = account.getType().name();

        return customerService.getCustomerById(customerId)
            .flatMap(customerData -> {
                String customerType = customerData.getType();

                if (customerType.equals(CustomerTypeEnum.BUSINESS.name())) {
                    if (accountType.equals(AccountTypeEnum.CHECKING.name())) {
                        return Mono.just(account);
                    } else {
                        return Mono.error(new RuntimeException("Solo se permite cuentas corrientes " +
                            " para cliente empresarial"));
                    }
                } else {
                    return accountRepository.existsByTypeAndAccountHoldersHolderId(accountType, customerId)
                        .flatMap(existsAccount -> {
                            if (Boolean.TRUE.equals(existsAccount)) {
                                return Mono.error(new RuntimeException("El Cliente Personal ya tiene una " +
                                    "cuenta del tipo ".concat(accountType)));
                            } else {
                                return Mono.just(account);
                            }
                        });
                }
            }).switchIfEmpty(Mono.error(new RuntimeException("No se encontraron datos del titular")));
    }
}



