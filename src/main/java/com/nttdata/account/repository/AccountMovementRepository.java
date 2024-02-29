package com.nttdata.account.repository;

import com.nttdata.account.model.account.AccountMovement;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface AccountMovementRepository extends ReactiveMongoRepository<AccountMovement, String> {

    Flux<AccountMovement> findByAccountNumber(String holderId);

}
