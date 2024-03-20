package com.nttdata.account.business.impl;

import com.nttdata.account.business.CustomerService;
import com.nttdata.account.client.CustomerClient;
import com.nttdata.account.model.customer.Customer;
import java.math.BigInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerClient customerClient;

    @Autowired
    CustomerServiceImpl(CustomerClient customerClient) {
        this.customerClient = customerClient;
    }

    @Override
    public Mono<Customer> findCustomer(BigInteger numberDocument) {
        return customerClient.getCustomer(numberDocument)
            .switchIfEmpty(Mono.defer(() -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                "No se encontraron datos del titular de la cuenta"))))
            .doOnSuccess(customerEntity -> log.info("Successful find - numberDocument: "
                .concat(numberDocument.toString())));
    }

    @Override
    public Mono<Customer> updateCustomer(Customer customer) {
        return customerClient.putCustomer(customer)
            .doOnSuccess(customerEntity -> log.info("Successful update Customer Id: "
                .concat(customerEntity.getId())));
    }

}

