package com.nttdata.account.business;

import com.nttdata.account.model.customer.Customer;
import java.math.BigInteger;
import reactor.core.publisher.Mono;

/**
 * Class: CustomerService. <br/>
 * <b>Bootcamp NTTDATA</b><br/>
 *
 * @author NTTDATA
 * @version 1.0
 *   <u>Developed by</u>:
 *   <ul>
 *   <li>Developer Carlos</li>
 *   </ul>
 * @since 1.0
 */
public interface CustomerService {
  Mono<Customer> findCustomer(BigInteger documentNumber);

  Mono<Customer> updateCustomer(Customer customer);
}
