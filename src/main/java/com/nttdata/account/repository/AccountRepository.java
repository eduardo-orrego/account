package com.nttdata.account.repository;

import com.nttdata.account.model.account.Account;
import java.math.BigInteger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Class: AccountRepository. <br/>
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
public interface AccountRepository {

  Mono<Account> findAccount(String accountId);

  Mono<Account> findAccount(BigInteger accountNumber);

  Flux<Account> findAccounts(BigInteger customerDocument);

  Mono<Account> saveAccount(Account account);

  Mono<Boolean> findExistsAccount(String type, BigInteger customerDocument);

  Mono<Boolean> findExistsAccount(BigInteger accountNumber);


}
