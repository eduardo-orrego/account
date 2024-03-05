package com.nttdata.account.client;

import com.nttdata.account.model.credit.Credit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
public class CreditClient {

    @Value("${microservices.customer.urlPaths.getCreditsByCustomerId}")
    private String urlPathGetCreditsByCustomerId;

    public Flux<Credit> getCreditsByCustomerId(String customerId) {
        return WebClient.create()
            .get()
            .uri(urlPathGetCreditsByCustomerId, customerId)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(Credit.class);
    }

}
