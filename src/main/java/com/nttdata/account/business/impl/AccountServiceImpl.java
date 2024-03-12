package com.nttdata.account.business.impl;

import com.nttdata.account.api.request.AccountRequest;
import com.nttdata.account.builder.AccountBuilder;
import com.nttdata.account.business.AccountService;
import com.nttdata.account.business.CreditCardService;
import com.nttdata.account.business.CustomerService;
import com.nttdata.account.enums.AccountTypeEnum;
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
    private final CreditCardService creditCardService;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, CustomerService customerService,
        CreditCardService creditCardService) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;
        this.creditCardService = creditCardService;
    }

    @Override
    public Mono<Account> saveAccount(AccountRequest accountRequest) {

        return customerService.getCustomerById(accountRequest.getCustomerId())
            .flatMap(customerData ->
                this.validationAccount(accountRequest, customerData)
                    .flatMap(account -> validationCustomerProfile(AccountBuilder.toEntity(account, null),
                        customerData)))
            .flatMap(accountRepository::save)
            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                "No se encontraron datos del titular")))
            .doOnSuccess(account -> log.info("Successful save - accountId: ".concat(account.getId())));
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

    private Mono<AccountRequest> validationAccount(AccountRequest accountRequest, Customer customerData) {

        if (customerData.getType().equals(CustomerTypeEnum.PERSONAL.name())) {
            return accountRepository.existsByTypeAndCustomerId(accountRequest.getType().name(),
                    customerData.getId())
                .flatMap(existsAccount -> {
                    if (Boolean.TRUE.equals(existsAccount)) {
                        return Mono.error(new RuntimeException("El Cliente Personal ya tiene una "
                            + "cuenta del tipo ".concat(accountRequest.getType().name())));
                    }
                    return Mono.just(accountRequest);
                });
        }
        if (customerData.getType().equals(CustomerTypeEnum.BUSINESS.name())) {
            if (!accountRequest.getType().name().equals(AccountTypeEnum.CHECKING.name())) {
                return Mono.error(new RuntimeException("Solo se permite cuentas corrientes "
                    + " para cliente empresarial"));
            }
            return Mono.just(accountRequest);

        }
        return Mono.just(accountRequest);
    }

    private Mono<Account> validationCustomerProfile(Account accountData, Customer customerData) {

        if (customerData.getType().equals(CustomerTypeEnum.PERSONAL.name())
            && accountData.getAvailableBalance().doubleValue() >= 500.00) {
            return this.validateUpdateCustomer(accountData, customerData, CustomerSubTypeEnum.VIP.name());
        }
        if (customerData.getType().equals(CustomerTypeEnum.BUSINESS.name())
            && accountData.getType().equals(AccountTypeEnum.CHECKING.name())) {
            return this.validateUpdateCustomer(accountData, customerData, CustomerSubTypeEnum.PYME.name());

        }
        return Mono.just(accountData);

    }

    private Mono<Account> validateUpdateCustomer(Account accountData, Customer customerData,
        String customerSubType) {

        return creditCardService.getCreditCards(accountData.getCustomerId())
            .hasElements()
            .flatMap(existsCard -> {
                if (Boolean.TRUE.equals(existsCard)) {
                    customerData.getPersonalInfo().setSubType(customerSubType);
                    return customerService.putCustomer(customerData)
                        .flatMap(result -> Mono.just(accountData));
                }
                return Mono.just(accountData);
            });
    }

}