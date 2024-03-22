package com.nttdata.account.client;

import com.nttdata.account.model.CreditCard;
import java.math.BigInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
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
@Slf4j
@Component
public class CreditCardClient {

  @Value("${microservices.creditCards.urlPaths.getCreditCards}")
  private String urlPathsGetCreditCards;

  public Flux<CreditCard> getCreditCards(BigInteger customerDocument) {

    return WebClient.create(urlPathsGetCreditCards)
      .get()
      .uri(uriBuilder -> uriBuilder
        .queryParam("customerDocument", customerDocument)
        .build())
      .accept(MediaType.APPLICATION_JSON)
      .retrieve()
      .bodyToFlux(CreditCard.class)
      .onErrorResume(WebClientResponseException.class, ex -> {
        if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
          log.info(String.format("El servicio Credit Cards no logro obtener datos " +
            "- customerDocument: %s", customerDocument));
          return Flux.empty();
        }
        log.info("Ocurrio un error al intentar comunicarse con el servicio Credit Cards");
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
          "Ocurrio un error al intentar comunicarse con el servicio Credit Cards");
      });
  }

}
