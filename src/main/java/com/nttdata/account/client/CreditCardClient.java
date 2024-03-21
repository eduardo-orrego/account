package com.nttdata.account.client;

import com.nttdata.account.model.CreditCard;
import java.math.BigInteger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

/**
 * Class: CreditCardClient. <br/>
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
public class CreditCardClient {

  @Value("${microservices.creditCards.urlPaths.getCreditCards}")
  private String urlPathsGetCreditCards;

  public Flux<CreditCard> getCreditCards(BigInteger documentNumber) {

    return WebClient.create()
      .get()
      .uri(uriBuilder -> uriBuilder
        .path(urlPathsGetCreditCards)
        .queryParam("documentNumber", documentNumber)
        .build())
      .accept(MediaType.APPLICATION_JSON)
      .retrieve()
      .bodyToFlux(CreditCard.class);
  }

}
