package com.nttdata.account.business;

import com.nttdata.account.model.CreditCard;
import reactor.core.publisher.Flux;

public interface CreditCardService {
    Flux<CreditCard> getCreditCards(String customerId);
}
