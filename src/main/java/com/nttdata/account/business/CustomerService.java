package com.nttdata.account.business;

import com.nttdata.account.model.customer.Customer;
import reactor.core.publisher.Mono;

public interface CustomerService {
    Mono<Customer> getCustomerById(String customerId);
}
