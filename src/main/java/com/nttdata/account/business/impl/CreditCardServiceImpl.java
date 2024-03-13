package com.nttdata.account.business.impl;

import com.nttdata.account.business.CreditCardService;
import com.nttdata.account.client.CreditCardClient;
import com.nttdata.account.model.CreditCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class CreditCardServiceImpl implements CreditCardService {

    @Autowired
    private CreditCardClient creditCardClient;

    @Override
    public Flux<CreditCard> getCreditCards(String customerId) {
        return creditCardClient.getCreditCards(customerId);
    }
}
