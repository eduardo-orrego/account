package com.nttdata.account.business.impl;

import com.nttdata.account.business.CreditCardService;
import com.nttdata.account.client.CreditCardClient;
import com.nttdata.account.model.CreditCard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class CreditCardServiceImpl implements CreditCardService {

    @Autowired
    private CreditCardClient creditCardClient;

    @Override
    public Flux<CreditCard> findCreditCards(String customerId) {
        return creditCardClient.getCreditCards(customerId)
            .doOnComplete(() -> log.info("Successful find Credit Card - customerId: ".concat(customerId)));
    }

    @Override
    public Mono<Boolean> findExistsCreditCard(String customerId) {
        return this.findCreditCards(customerId)
            .hasElements()
            .doOnSuccess(exists -> log.info("Successful find exists Credit Card - customerId: ".concat(customerId)));

    }
}
