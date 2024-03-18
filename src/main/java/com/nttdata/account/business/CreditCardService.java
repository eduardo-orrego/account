package com.nttdata.account.business;

import com.nttdata.account.model.CreditCard;
import java.math.BigInteger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditCardService {
    Flux<CreditCard> findCreditCards(BigInteger documentNumber);

    Mono<Boolean> findExistsCreditCard(BigInteger documentNumber);
}
