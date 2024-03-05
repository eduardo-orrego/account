package com.nttdata.account.business.impl;

import com.nttdata.account.business.AccountService;
import com.nttdata.account.business.CreditService;
import com.nttdata.account.business.CustomerService;
import com.nttdata.account.model.account.Account;
import com.nttdata.account.model.account.AccountHolder;
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

        String customerId = account.getAccountHolders().stream()
            .filter(accountHolder -> accountHolder.getHolderType().name().equals(HolderTypeEnum.PRIMARY.name()))
            .findFirst().map(AccountHolder::getHolderId).orElse("");

        String accountType = account.getType().name();

        return customerService.getCustomerById(customerId)
            .flatMap(customerData -> {
                String customerType = customerData.getType();

                if (customerType.equals(CustomerTypeEnum.PERSONAL.name())) {
                    return accountRepository.existsByTypeAndAccountHoldersHolderId(accountType, customerId)
                        .flatMap(existsAccount -> {
                            if (Boolean.TRUE.equals(existsAccount)) {
                                return Mono.error(new RuntimeException("El Cliente Personal ya tiene una " +
                                    "cuenta del tipo ".concat(accountType)));
                            }

                            if(account.getAvailableBalance().doubleValue() >= 500.00){
                                customerData.setSubType(CustomerSubTypeEnum.PYME.name());
                                return customerService.putCustomer(customerData)
                                    .flatMap(customer -> Mono.just(account));
                            }
                            return Mono.just(account);
                        });
                }

                if (customerType.equals(CustomerTypeEnum.BUSINESS.name())) {
                    if (!accountType.equals(AccountTypeEnum.CHECKING.name())) {
                        return Mono.error(new RuntimeException("Solo se permite cuentas corrientes " +
                            " para cliente empresarial"));
                    }
                    return creditService.getCreditsByCustomerId(customerId)
                        .any(credit -> credit.getType().equals(CreditTypeEnum.CREDIT_CARD.name()))
                        .flatMap(aBoolean1 -> {
                            if (aBoolean1) {
                                customerData.setSubType(CustomerSubTypeEnum.PYME.name());
                                return customerService.putCustomer(customerData)
                                    .flatMap(customer -> Mono.just(account));
                            }
                            return Mono.just(account);
                        });

                }
                return Mono.just(account);
            }).switchIfEmpty(Mono.error(new RuntimeException("No se encontraron datos del titular")));
    }
}