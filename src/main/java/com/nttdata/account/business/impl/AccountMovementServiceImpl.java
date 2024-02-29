package com.nttdata.account.business.impl;

import com.nttdata.account.business.AccountMovementService;
import com.nttdata.account.model.account.AccountMovement;
import com.nttdata.account.repository.AccountMovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountMovementServiceImpl implements AccountMovementService {

    private final AccountMovementRepository accountMovementRepository;

    @Autowired
    public AccountMovementServiceImpl(AccountMovementRepository accountMovementRepository) {
        this.accountMovementRepository = accountMovementRepository;
    }

    @Override
    public Mono<AccountMovement> saveMovementAccount(AccountMovement accountMovement) {
        return accountMovementRepository.save(accountMovement);
    }

    @Override
    public Flux<AccountMovement> getMovementsAccount(String accountNumber) {
        return accountMovementRepository.findByAccountNumber(accountNumber);
    }

}
