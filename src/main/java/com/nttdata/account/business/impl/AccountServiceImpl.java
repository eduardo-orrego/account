package com.nttdata.account.business.impl;

import com.nttdata.account.business.AccountService;
import com.nttdata.account.business.CreditService;
import com.nttdata.account.business.CustomerService;
import com.nttdata.account.model.account.Account;
import com.nttdata.account.model.account.AccountHolder;
import com.nttdata.account.model.customer.Customer;
import com.nttdata.account.model.enums.AccountTypeEnum;
import com.nttdata.account.model.enums.CreditTypeEnum;
import com.nttdata.account.model.enums.CustomerSubTypeEnum;
import com.nttdata.account.model.enums.CustomerTypeEnum;
import com.nttdata.account.model.enums.HolderTypeEnum;
import com.nttdata.account.repository.AccountRepository;
import java.math.BigInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerService customerService;
    private final CreditService creditService;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, CustomerService customerService,
        CreditService creditService) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;
        this.creditService = creditService;
    }

    @Override
    public Mono<Account> getAccountByAccountNumber(BigInteger accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
            .switchIfEmpty(Mono.error(new RuntimeException("Numero de cuenta no existe")));
    }

    @Override
    public Flux<Account> getAccountsByHolderId(String holderId) {

        return accountRepository.findByAccountHoldersHolderId(holderId)
            .switchIfEmpty(Mono.error(new RuntimeException("No se encontraron cuentas asociadas al cliente "
                .concat(holderId))));
    }

    @Override
    public Mono<Account> saveAccount(Account account) {
        return validationData(account)
            .flatMap(accountRepository::save);
    }

    @Override
    public Mono<Account> updateAccount(Account account) {
        return accountRepository.save(account);
    }

    private Mono<Account> validationData(Account account) {

        return customerService.getCustomerById(this.getHolderId(account))
            .flatMap(customerData -> {
                if (customerData.getType().equals(CustomerTypeEnum.PERSONAL.name())) {
                    return this.validatePersonalAccount(account, customerData);
                }
                if (customerData.getType().equals(CustomerTypeEnum.BUSINESS.name())) {
                    return this.validateBusinessAccount(account, customerData);
                }
                return Mono.just(account);
            }).switchIfEmpty(Mono.error(new RuntimeException("No se encontraron datos del titular")));
    }

    private String getHolderId(Account account) {
        return account.getAccountHolders().stream()
            .filter(accountHolder -> accountHolder.getHolderType().name().equals(HolderTypeEnum.PRIMARY.name()))
            .findFirst().map(AccountHolder::getHolderId).orElse("");
    }

    private Mono<Account> validateBusinessAccount(Account accountData, Customer customerData) {
        if (!accountData.getType().name().equals(AccountTypeEnum.CHECKING.name())) {
            return Mono.error(new RuntimeException("Solo se permite cuentas corrientes " +
                " para cliente empresarial"));
        }
        return creditService.getCreditsByCustomerId(customerData.getId())
            .any(credit -> credit.getType().equals(CreditTypeEnum.CREDIT_CARD.name()))
            .flatMap(existsAccount -> {
                if (Boolean.TRUE.equals(existsAccount)) {
                    customerData.setSubType(CustomerSubTypeEnum.PYME.name());
                    return customerService.putCustomer(customerData)
                        .flatMap(res -> Mono.just(accountData));
                }
                return Mono.just(accountData);
            });
    }

    private Mono<Account> validatePersonalAccount(Account accountData, Customer customerData) {
        return accountRepository.existsByTypeAndAccountHoldersHolderId(accountData.getType().name(),
                customerData.getId())
            .flatMap(existsAccount -> {
                if (Boolean.TRUE.equals(existsAccount)) {
                    return Mono.error(new RuntimeException("El Cliente Personal ya tiene una " +
                        "cuenta del tipo ".concat(accountData.getType().name())));
                }
                if (accountData.getAvailableBalance().doubleValue() >= 500.00) {
                    customerData.setSubType(CustomerSubTypeEnum.PYME.name());
                    return customerService.putCustomer(customerData)
                        .flatMap(result -> Mono.just(accountData));
                }
                return Mono.just(accountData);
            });
    }
}