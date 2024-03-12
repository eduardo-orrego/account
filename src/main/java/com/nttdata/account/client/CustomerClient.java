package com.nttdata.account.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.account.model.customer.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Slf4j
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
//        printJson(customer, urlPathPutCustomer);

        return WebClient.create()
            .put()
            .uri(urlPathPutCustomer, customer.getId())
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(customer)
            .retrieve()
            .bodyToMono(Customer.class);
    }

//    static ObjectMapper mapper = new ObjectMapper();
//
//    public static void printJson(Object object, String uri) {
//        try {
//            if (!uri.isEmpty()) {
//                log.info("\nuri:{}", uri);
//            }
//            if (object != null) {
//                log.info("\nrequest: {}", mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object));
//            }
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//    }
}
