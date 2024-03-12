package com.nttdata.account.business;

import com.nttdata.account.model.Credit;
import reactor.core.publisher.Flux;

public interface CreditService {

    Flux<Credit> getCredits(String customerId);

}
