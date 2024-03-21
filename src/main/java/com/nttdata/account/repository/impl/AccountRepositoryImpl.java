package com.nttdata.account.repository.impl;

import com.nttdata.account.model.account.Account;
import com.nttdata.account.repository.AccountReactiveMongodb;
import com.nttdata.account.repository.AccountRepository;
import java.math.BigInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Class: AccountRepositoryImpl. <br/>
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
@Repository
public class AccountRepositoryImpl implements AccountRepository {

  @Autowired
  private AccountReactiveMongodb accountReactiveMongodb;

  @Override
  public Mono<Account> findAccount(String accountId) {
    return accountReactiveMongodb.findById(accountId)
      .doOnSuccess(result -> log.info("Successful find - accountId: ".concat(accountId)));
  }

  @Override
  public Mono<Account> findAccount(BigInteger accountNumber) {
    return accountReactiveMongodb.findByAccountNumber(accountNumber)
      .doOnSuccess(accountEntity -> log.info(
        "Successful find - accountNumber: ".concat(accountNumber.toString())));
  }

  @Override
  public Flux<Account> findAccounts(BigInteger customerDocument) {
    return accountReactiveMongodb.findByAccountHoldersCustomerDocument(customerDocument)
      .doOnComplete(
        () -> log.info("Successful find - customerDocument: ".concat(customerDocument.toString())));
  }


  @Override
  public Mono<Account> saveAccount(Account account) {
    return accountReactiveMongodb.save(account)
      .doOnSuccess(
        accountEntity -> log.info("Successful save - accountId: ".concat(accountEntity.getId())));
  }

  @Override
  public Mono<Boolean> findExistsAccount(BigInteger accountNumber) {
    return accountReactiveMongodb.existsByAccountNumber(accountNumber)
      .doOnSuccess(exists -> log.info("Successful find exists - accountNumber: "
        .concat(accountNumber.toString())));
  }

  @Override
  public Mono<Boolean> findExistsAccount(String type, BigInteger customerDocument) {
    return accountReactiveMongodb.existsByTypeAndAccountHoldersCustomerDocument(type,
        customerDocument)
      .doOnSuccess(exists -> log.info("Successful find exists - customerDocument: "
        .concat(customerDocument.toString())));
  }

}
