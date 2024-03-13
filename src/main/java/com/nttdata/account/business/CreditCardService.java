package com.nttdata.account.business;

import com.nttdata.account.model.CreditCard;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditCardService {
    Flux<CreditCard> findCreditCards(String customerId);

    Mono<Boolean> findExistsCreditCard(String customerId);
}
