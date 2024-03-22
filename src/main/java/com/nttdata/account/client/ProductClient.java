package com.nttdata.account.client;

import com.nttdata.account.model.Product;
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
 * Class: ProductClient. <br/>
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
public class ProductClient {

  @Value("${microservices.products.urlPaths.getProduct}")
  private String urlPathGetProducts;

  public Mono<Product> getProducts(String productType) {

    return WebClient.create(urlPathGetProducts)
      .get()
      .uri(uriBuilder -> uriBuilder
        .queryParam("productType", productType)
        .build())
      .accept(MediaType.APPLICATION_JSON)
      .retrieve()
      .bodyToMono(Product.class)
      .onErrorResume(WebClientResponseException.class, ex -> {
        if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
          log.info(String.format("El Servicio Products no logro obtener datos " +
            "- productType: %s", productType));
          return Mono.empty();
        }
        log.info("Ocurrio un error al intentar comunicarse con el servicio Productos");
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
          "Ocurrio un error al intentar comunicarse con el servicio Products");
      });

  }

}
