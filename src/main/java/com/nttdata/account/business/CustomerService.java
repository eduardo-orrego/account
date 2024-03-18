package com.nttdata.account.business;

import com.nttdata.account.model.customer.Customer;
import java.math.BigInteger;
import reactor.core.publisher.Mono;

public interface CustomerService {
    Mono<Customer> findCustomer(BigInteger documentNumber);

    Mono<Customer> updateCustomer(Customer customer);
}
