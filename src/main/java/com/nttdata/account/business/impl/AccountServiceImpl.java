package com.nttdata.account.business.impl;

import com.nttdata.account.api.request.AccountRequest;
import com.nttdata.account.builder.AccountBuilder;
import com.nttdata.account.business.AccountService;
import com.nttdata.account.business.CreditService;
import com.nttdata.account.business.CustomerService;
import com.nttdata.account.enums.AccountTypeEnum;
import com.nttdata.account.enums.CreditTypeEnum;
import com.nttdata.account.enums.CustomerSubTypeEnum;
import com.nttdata.account.enums.CustomerTypeEnum;
import com.nttdata.account.model.account.Account;
import com.nttdata.account.model.customer.Customer;
import com.nttdata.account.repository.AccountRepository;
import java.math.BigInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
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
    public Mono<Account> saveAccount(AccountRequest accountRequest) {
        return validationData(accountRequest)
            .map(customer -> AccountBuilder.toEntity(customer, null))
            .flatMap(accountRepository::save)
            .doOnSuccess(customer -> log.info("Successful save - accountId: ".concat(customer.getId())));
    }

    @Override
    public Mono<Account> updateAccount(AccountRequest accountRequest, String accountId) {
        return accountRepository.existsById(accountId)
            .flatMap(aBoolean -> {
                if (Boolean.TRUE.equals(aBoolean)) {
                    return accountRepository.save(AccountBuilder.toEntity(accountRequest, accountId));
                }
                return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found - "
                    + "accountId: ".concat(accountId)));
            })
            .doOnSuccess(account -> log.info("Successful update - accountId: ".concat(accountId)));
    }


    @Override
    public Mono<Account> getAccountByAccountNumber(BigInteger accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found - "
                + "accountNumber: ".concat(accountNumber.toString()))))
            .doOnSuccess(customer -> log.info("Successful search - accountNumber: ".concat(accountNumber.toString())));
    }

    @Override
    public Flux<Account> getAccountsByCustomerId(String customerId) {

        return accountRepository.findByCustomerId(customerId)
            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found - "
                + "holderId: ".concat(customerId))))
            .doOnComplete(() -> log.info("Successful search - holderId: ".concat(customerId)));
    }

    @Override
    public Mono<Void> deleteAccount(String accountId) {
        return accountRepository.existsById(accountId)
            .flatMap(aBoolean -> {
                if (Boolean.TRUE.equals(aBoolean)) {
                    return accountRepository.deleteById(accountId);
                }
                return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found - " +
                    "accountId: ".concat(accountId)));
            })
            .doOnSuccess(customer -> log.info("Successful delete - accountId: ".concat(accountId)));
    }

    private Mono<AccountRequest> validationData(AccountRequest accountRequest) {

        return customerService.getCustomerById(accountRequest.getCustomerId())
            .flatMap(customerData -> {
                if (customerData.getType().equals(CustomerTypeEnum.PERSONAL.name())) {
                    return this.validatePersonalAccount(accountRequest, customerData);
                }
                if (customerData.getType().equals(CustomerTypeEnum.BUSINESS.name())) {
                    return this.validateBusinessAccount(accountRequest, customerData);
                }
                return Mono.just(accountRequest);
            }).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                "No se encontraron datos del titular")));
    }

    private Mono<AccountRequest> validatePersonalAccount(AccountRequest accountData, Customer customerData) {
        return accountRepository.existsByTypeAndCustomerId(accountData.getType().name(),
                customerData.getId())
            .flatMap(existsAccount -> {
                if (Boolean.TRUE.equals(existsAccount)) {
                    return Mono.error(new RuntimeException("El Cliente Personal ya tiene una "
                        + "cuenta del tipo ".concat(accountData.getType().name())));
                }
                if (accountData.getAvailableBalance().doubleValue() >= 500.00) {
                    customerData.getPersonalInfo().setSubType(CustomerSubTypeEnum.VIP.name());
                    return customerService.putCustomer(customerData)
                        .flatMap(result -> Mono.just(accountData));
                }
                return Mono.just(accountData);
            });
    }

    private Mono<AccountRequest> validateBusinessAccount(AccountRequest accountData, Customer customerData) {

        if (!accountData.getType().name().equals(AccountTypeEnum.CHECKING.name())) {
            return Mono.error(new RuntimeException("Solo se permite cuentas corrientes "
                + " para cliente empresarial"));
        }
        return creditService.getCreditsByCustomerId(customerData.getId())
            .any(credit -> credit.getType().equals(CreditTypeEnum.CREDIT_CARD.name()))
            .flatMap(existsAccount -> {
                if (Boolean.TRUE.equals(existsAccount)) {
                    customerData.getBusinessInfo().setSubType(CustomerSubTypeEnum.PYME.name());
                    return customerService.putCustomer(customerData)
                        .flatMap(res -> Mono.just(accountData));
                }
                return Mono.just(accountData);
            });
    }

}