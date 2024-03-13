package com.nttdata.account.business;

import com.nttdata.account.model.customer.Customer;
import reactor.core.publisher.Mono;

public interface CustomerService {
    Mono<Customer> findCustomer(String customerId);

    Mono<Customer> updateCustomer(Customer customer);
}
