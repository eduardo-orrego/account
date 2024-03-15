package com.nttdata.account.client;

import com.nttdata.account.model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
public class ProductClient {

    @Value("${microservices.customers.urlPaths.getProducts}")
    private String urlPathGetProducts;

    public Flux<Product> getProducts(String typeProduct) {

        return WebClient.create()
            .get()
            .uri(uriBuilder -> uriBuilder
                .queryParam("type", typeProduct)
                .build())
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(Product.class);

    }

}
