package com.nttdata.account.business.impl;

import com.nttdata.account.business.CreditService;
import com.nttdata.account.client.CreditClient;
import com.nttdata.account.model.Credit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class CreditServiceImpl implements CreditService {

    private final CreditClient creditClient;

    @Autowired
    public CreditServiceImpl(CreditClient creditClient) {
        this.creditClient = creditClient;
    }

    @Override
    public Flux<Credit> getCredits(String customerId) {
        return creditClient.getCredits(customerId);
    }

}
