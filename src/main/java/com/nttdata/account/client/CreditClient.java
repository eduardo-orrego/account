package com.nttdata.account.client;

import com.nttdata.account.model.Credit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
public class CreditClient {

    @Value("${microservices.credit.urlPaths.getCreditsByCustomerId}")
    private String urlPathGetCredits;

    public Flux<Credit> getCredits(String customerId) {
        return WebClient.create()
            .get()
            .uri(uriBuilder -> uriBuilder.path(urlPathGetCredits)
                .queryParam("customerId", customerId)
                .build())
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(Credit.class);
    }

}
