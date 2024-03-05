package com.nttdata.account.client;

import com.nttdata.account.model.customer.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class CustomerClient {

    @Value("${microservices.customer.urlPaths.getCustomerById}")
    private String urlPathGetCustomerById;

    @Value("${microservices.customer.urlPaths.putCustomer}")
    private String urlPathPutCustomer;

    public Mono<Customer> getCustomerById(String customerId) {
        return WebClient.create()
            .get()
            .uri(urlPathGetCustomerById, customerId)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(Customer.class);
    }

    public Mono<Customer> putCustomer(Customer customer) {
        return WebClient.create()
            .put()
            .uri(urlPathPutCustomer)
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(customer)
            .retrieve()
            .bodyToMono(Customer.class);
    }

}
