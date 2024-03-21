package com.nttdata.account.business;

import com.nttdata.account.api.request.AccountRequest;
import com.nttdata.account.model.account.Account;
import java.math.BigInteger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Class: AccountService. <br/>
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
public interface AccountService {

  Mono<Account> saveAccount(AccountRequest account);

  Mono<Account> updateAccount(AccountRequest account, String accountId);

  Mono<Account> getAccount(BigInteger accountNumber);

  Flux<Account> getAccounts(BigInteger documentNumber);

}
