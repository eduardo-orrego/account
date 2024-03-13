package com.nttdata.account.client;

import com.nttdata.account.model.CreditCard;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;

@Component
public class CreditCardClient {

    @Value("${microservices.creditCards.urlPaths.getCreditCardsByCustomerId}")
    private String urlPathsGetCreditCards;

    public Flux<CreditCard> getCreditCards(String customerId) {
        MultiValueMap<String, String> mapParams = new LinkedMultiValueMap<>();
        mapParams.add("customerId", customerId);

        String path = UriComponentsBuilder.fromUriString(urlPathsGetCreditCards)
            .queryParams(mapParams)
            .toUriString();

        return WebClient.create()
            .get()
            .uri(path)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(CreditCard.class);
    }

}
