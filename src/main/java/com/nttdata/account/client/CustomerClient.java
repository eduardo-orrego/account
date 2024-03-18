package com.nttdata.account.client;

import com.nttdata.account.model.customer.Customer;
import java.math.BigInteger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class CustomerClient {

    @Value("${microservices.customers.urlPaths.getCustomer}")
    private String urlPathGetCustomer;

    @Value("${microservices.customers.urlPaths.putCustomer}")
    private String urlPathPutCustomer;

    public Mono<Customer> getCustomer(BigInteger documentNumber) {
        return WebClient.create()
            .get()
            .uri(urlPathGetCustomer, documentNumber)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(Customer.class);
    }

    public Mono<Customer> putCustomer(Customer customer) {

        return WebClient.create()
            .put()
            .uri(urlPathPutCustomer, customer.getId())
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(customer)
            .retrieve()
            .bodyToMono(Customer.class);
    }

}
