package com.nttdata.account.client;

import com.nttdata.account.model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
public class ProductClient {

    @Value("${microservices.credit.urlPaths.getProductByType}")
    private String urlPathGetProducts;

    public Flux<Product> getProduct(String productType) {
        return WebClient.create()
            .get()
            .uri(uriBuilder -> uriBuilder.path(urlPathGetProducts)
                .queryParam("productType", productType)
                .build())
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(Product.class);
    }

}
