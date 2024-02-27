package com.nttdata.account.business;

import com.nttdata.account.model.AccountMovement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountMovementService {

    Mono<AccountMovement> saveMovementAccount(AccountMovement accountMovement);

    Flux<AccountMovement> getMovementsAccount(String accountNumber);

}
