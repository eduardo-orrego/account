package com.nttdata.account.business.impl;

import com.nttdata.account.api.request.AccountHolderRequest;
import com.nttdata.account.api.request.AccountRequest;
import com.nttdata.account.builder.AccountBuilder;
import com.nttdata.account.business.AccountService;
import com.nttdata.account.business.CreditCardService;
import com.nttdata.account.business.CustomerService;
import com.nttdata.account.business.ProductService;
import com.nttdata.account.enums.AccountTypeEnum;
import com.nttdata.account.enums.CustomerSubTypeEnum;
import com.nttdata.account.enums.CustomerTypeEnum;
import com.nttdata.account.enums.HolderTypeEnum;
import com.nttdata.account.model.Product;
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

/**
 * Class: AccountServiceImpl. <br/>
 * <b>Bootcamp NTTDATA</b><br/>
 *
 * @author NTTDATA
 * @version 1.0
 *   <u>Developed by</u>:
 *   <ul>
 *   <li>Developer Carlos</li>
 *   </ul>
 * @since 1.0
 */
@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

  @Autowired
  private ProductService productService;

  @Autowired
  private CustomerService customerService;

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private CreditCardService creditCardService;

  @Override
  public Mono<Account> saveAccount(AccountRequest accountRequest) {
    return accountRepository.findExistsAccount(accountRequest.getAccountNumber())
      .flatMap(aBoolean -> {
        if (Boolean.FALSE.equals(aBoolean)) {
          return customerService.findCustomer(this.getCustomerDocument(accountRequest.getAccountHolders()))
            .flatMap(customerData -> productService.findProduct(accountRequest.getType().name())
              .flatMap(product -> this.validationAccount(accountRequest, customerData, product)
                .map(accountValidated -> AccountBuilder.toEntity(accountValidated, product))
                .flatMap(accountRepository::saveAccount)
                .flatMap(accountEntity ->
                  this.validationUpdateCustomerProfile(accountRequest, customerData, product)
                    .thenReturn(accountEntity))));
        }
        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "There is another Account with the same Account Number: "
            .concat(accountRequest.getAccountNumber().toString())));
      });
  }

  @Override
  public Mono<Account> updateAccount(AccountRequest accountRequest, String accountId) {
    return accountRepository.findAccount(accountId)
      .flatMap(account ->
        accountRepository.saveAccount(AccountBuilder.toEntity(accountRequest, account)))
      .switchIfEmpty(Mono.defer(() -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
        "Account not found - accountId: ".concat(accountId)))));
  }


  @Override
  public Mono<Account> getAccount(BigInteger accountNumber) {
    return accountRepository.findAccount(accountNumber)
      .switchIfEmpty(Mono.defer(() -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
        "Account not found - accountNumber: ".concat(accountNumber.toString())))));
  }

  @Override
  public Flux<Account> getAccounts(BigInteger documentNumber) {

    return accountRepository.findAccounts(documentNumber)
      .switchIfEmpty(
        Mono.defer(() -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
          "Account not found - documentNumber: ".concat(documentNumber.toString())))));
  }

  private BigInteger getCustomerDocument(List<AccountHolderRequest> accountHolders) {
    return accountHolders.stream()
      .filter(accountHolder -> accountHolder.getHolderType().equals(HolderTypeEnum.PRIMARY))
      .findFirst()
      .map(AccountHolderRequest::getCustomerDocument)
      .orElse(BigInteger.valueOf(0L));
  }

  private Mono<AccountRequest> validationAccount(AccountRequest accountRequest,
    Customer customerData,
    Product product) {

    if (customerData.getType().equals(CustomerTypeEnum.PERSONAL.name())) {
      return accountRepository.findExistsAccount(accountRequest.getType().name(),
          customerData.getIdentificationDocument().getNumber())
        .flatMap(existsAccount -> Boolean.TRUE.equals(existsAccount)
          ? Mono.error(new RuntimeException("El Cliente Personal ya tiene una "
          + "cuenta del tipo ".concat(accountRequest.getType().name())))
          : Mono.just(accountRequest)
        );
    }

    if (accountRequest.getType().equals(AccountTypeEnum.SAVINGS)
      && accountRequest.getAmount().compareTo(product.getMinimumOpeningAmount()) < 0) {
      return Mono.error(new RuntimeException("Se necesita un monto mínimo de "
        .concat(product.getMinimumOpeningAmount().toString())
        .concat(" para aperturar una cuenta de ahorros")));
    }

    if (customerData.getType().equals(CustomerTypeEnum.BUSINESS.name())
      && !accountRequest.getType().equals(AccountTypeEnum.CHECKING)) {
      return Mono.error(new RuntimeException("Solo se permite cuentas corrientes "
        + " para cliente empresarial"));

    }

    return Mono.just(accountRequest);
  }

  private Mono<Customer> validationUpdateCustomerProfile(AccountRequest accountRequest,
    Customer customerData,
    Product product) {

    return creditCardService.findExistsCreditCard(
        this.getCustomerDocument(accountRequest.getAccountHolders()))
      .flatMap(existsCard -> {
        if (Boolean.TRUE.equals(existsCard)) {
          this.setCustomerDataProfile(customerData, accountRequest, product);
          return customerService.updateCustomer(customerData);
        }
        return Mono.just(customerData);
      });
  }

  private void setCustomerDataProfile(Customer customerData, AccountRequest accountRequest,
    Product product) {
    if (customerData.getType().equals(CustomerTypeEnum.BUSINESS.name())) {
      customerData.getBusinessInfo().setSubType(CustomerSubTypeEnum.MYPE.name());
    }

    if (customerData.getType().equals(CustomerTypeEnum.PERSONAL.name())
      && accountRequest.getType().equals(AccountTypeEnum.SAVINGS)
      && accountRequest.getAvailableBalance().compareTo(
      product.getMinimumAmountPersonalVip()) >= 0) {
      customerData.getPersonalInfo().setSubType(CustomerSubTypeEnum.VIP.name());
    }
  }

}
