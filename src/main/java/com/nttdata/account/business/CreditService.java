package com.nttdata.account.business;

import com.nttdata.account.model.credit.Credit;
import reactor.core.publisher.Flux;

public interface CreditService {

    Flux<Credit> getCreditsByCustomerId(String customerId);

}
