package com.nttdata.account.business.impl;

import com.nttdata.account.api.request.AccountHolderRequest;
import com.nttdata.account.api.request.AccountRequest;
import com.nttdata.account.builder.AccountBuilder;
import com.nttdata.account.business.AccountService;
import com.nttdata.account.business.CreditCardService;
import com.nttdata.account.business.CustomerService;
import com.nttdata.account.enums.AccountTypeEnum;
import com.nttdata.account.enums.CustomerSubTypeEnum;
import com.nttdata.account.enums.CustomerTypeEnum;
import com.nttdata.account.enums.HolderTypeEnum;
import com.nttdata.account.model.account.Account;
import com.nttdata.account.model.customer.Customer;
import com.nttdata.account.repository.AccountRepository;
import java.math.BigInteger;
import java.util.List;
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

    private final CustomerService customerService;
    private final AccountRepository accountRepository;
    private final CreditCardService creditCardService;

    @Autowired
    AccountServiceImpl(CustomerService customerService, AccountRepository accountRepository,
        CreditCardService creditCardService) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;
        this.creditCardService = creditCardService;
    }

    @Override
    public Mono<Account> saveAccount(AccountRequest accountRequest) {
        return customerService.findCustomer(this.getHolderId(accountRequest.getAccountHolders()))
            .flatMap(customerData ->
                this.validationAccount(accountRequest, customerData)
                    .map(accountValidated -> AccountBuilder.toEntity(accountValidated, null))
                    .flatMap(accountRepository::saveAccount)
                    .flatMap(accountEntity ->
                        this.validationUpdateCustomerProfile(accountRequest, customerData)
                            .thenReturn(accountEntity))
            );
    }

    @Override
    public Mono<Account> updateAccount(AccountRequest accountRequest, String accountId) {
        return accountRepository.findExistsAccount(accountId)
            .flatMap(aBoolean -> Boolean.TRUE.equals(aBoolean)
                ? accountRepository.saveAccount(AccountBuilder.toEntity(accountRequest, accountId))
                : Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found - "
                    + "accountId: ".concat(accountId))));
    }


    @Override
    public Mono<Account> getAccountByAccountNumber(BigInteger accountNumber) {
        return accountRepository.findAccount(accountNumber)
            .switchIfEmpty(Mono.defer(() -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Account not found - accountNumber: ".concat(accountNumber.toString())))));
    }

    @Override
    public Flux<Account> getAccountsByCustomerId(String customerId) {

        return accountRepository.findAccounts(customerId)
            .switchIfEmpty(
                Mono.defer(() -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Account not found - holderId: ".concat(customerId)))));
    }

    private String getHolderId(List<AccountHolderRequest> accountHolders) {
        return accountHolders.stream()
            .filter(accountHolder -> accountHolder.getHolderType().equals(HolderTypeEnum.PRIMARY))
            .findFirst()
            .map(AccountHolderRequest::getHolderId)
            .orElse("nothing");
    }

    private Mono<AccountRequest> validationAccount(AccountRequest accountRequest, Customer customerData) {

        if (customerData.getType().equals(CustomerTypeEnum.PERSONAL.name())) {
            return accountRepository.findExistsAccount(accountRequest.getType().name(),
                    customerData.getId())
                .flatMap(existsAccount -> Boolean.TRUE.equals(existsAccount)
                    ? Mono.error(new RuntimeException("El Cliente Personal ya tiene una "
                    + "cuenta del tipo ".concat(accountRequest.getType().name())))
                    : Mono.just(accountRequest)
                );
        }

        if (customerData.getType().equals(CustomerTypeEnum.BUSINESS.name())
            && !accountRequest.getType().equals(AccountTypeEnum.CHECKING)) {
            return Mono.error(new RuntimeException("Solo se permite cuentas corrientes "
                + " para cliente empresarial"));

        }
        return Mono.just(accountRequest);
    }

    private Mono<Customer> validationUpdateCustomerProfile(AccountRequest accountRequest, Customer customerData) {

        return creditCardService.findExistsCreditCard(this.getHolderId(accountRequest.getAccountHolders()))
            .flatMap(existsCard -> {
                if (Boolean.TRUE.equals(existsCard)) {
                    this.setCustomerDataProfile(customerData, accountRequest);
                    return customerService.updateCustomer(customerData);
                }
                return Mono.just(customerData);
            });
    }

    private void setCustomerDataProfile(Customer customerData, AccountRequest accountRequest) {
        if (customerData.getType().equals(CustomerTypeEnum.BUSINESS.name())) {
            customerData.getBusinessInfo().setSubType(CustomerSubTypeEnum.MYPE.name());
        }

        if (customerData.getType().equals(CustomerTypeEnum.PERSONAL.name())
            && accountRequest.getType().equals(AccountTypeEnum.SAVINGS)
            && accountRequest.getAvailableBalance().doubleValue() >= 500.00) {
            customerData.getPersonalInfo().setSubType(CustomerSubTypeEnum.VIP.name());
        }
    }

}
