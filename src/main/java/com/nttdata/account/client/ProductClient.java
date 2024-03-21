package com.nttdata.account.client;

import com.nttdata.account.model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
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
@Component
public class ProductClient {

  @Value("${microservices.products.urlPaths.getProduct}")
  private String urlPathGetProducts;

  public Mono<Product> getProducts(String typeProduct) {

    return WebClient.create()
      .get()
      .uri(uriBuilder -> uriBuilder
        .path(urlPathGetProducts)
        .queryParam("type", typeProduct)
        .build())
      .accept(MediaType.APPLICATION_JSON)
      .retrieve()
      .bodyToMono(Product.class);

  }

}
