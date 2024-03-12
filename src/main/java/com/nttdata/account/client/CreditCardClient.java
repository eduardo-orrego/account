package com.nttdata.account.client;

import com.nttdata.account.model.CreditCard;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
public class CreditCardClient {

    @Value("${microservices.creditCards.urlPaths.getCreditCardsByCustomerId}")
    private String urlPathsGetCreditCards;

    public Flux<CreditCard> getCreditCards(String customerId) {
        return WebClient.create()
            .get()
            .uri(uriBuilder -> uriBuilder.path(urlPathsGetCreditCards)
                .queryParam("customerId", customerId)
                .build())
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(CreditCard.class);
    }

}
