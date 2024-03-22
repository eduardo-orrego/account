package com.nttdata.account.client;

import com.nttdata.account.model.customer.Customer;
import java.math.BigInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

/**
 * Class: CustomerClient. <br/>
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
@Slf4j
@Component
public class CustomerClient {

  @Value("${microservices.customers.urlPaths.getCustomer}")
  private String urlPathGetCustomer;

  @Value("${microservices.customers.urlPaths.putCustomer}")
  private String urlPathPutCustomer;

  public Mono<Customer> getCustomer(BigInteger documentNumber) {
    return WebClient.create(urlPathGetCustomer)
      .get()
      .uri(uriBuilder -> uriBuilder
        .queryParam("documentNumber", documentNumber)
        .build())
      .accept(MediaType.APPLICATION_JSON)
      .retrieve()
      .bodyToMono(Customer.class)
      .onErrorResume(WebClientResponseException.class, ex -> {
        if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
          log.info(String.format("El Servicio Customers no logro obtener datos " +
            "- documentNumber: %s", documentNumber));
          return Mono.empty();
        }
        log.info("Ocurrio un error al intentar comunicarse con el servicio Customers");
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
          "Ocurrio un error al intentar comunicarse con el servicio Customers");
      });
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
