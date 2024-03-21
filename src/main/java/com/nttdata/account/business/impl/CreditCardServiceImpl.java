package com.nttdata.account.business.impl;

import com.nttdata.account.business.CreditCardService;
import com.nttdata.account.client.CreditCardClient;
import com.nttdata.account.model.CreditCard;
import java.math.BigInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Class: CreditCardServiceImpl. <br/>
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
@Service
public class CreditCardServiceImpl implements CreditCardService {

  @Autowired
  private CreditCardClient creditCardClient;

  @Override
  public Flux<CreditCard> findCreditCards(BigInteger documentNumber) {
    return creditCardClient.getCreditCards(documentNumber)
      .doOnComplete(() -> log.info("Successful find Credit Card - documentNumber: "
        .concat(documentNumber.toString())));
  }

  @Override
  public Mono<Boolean> findExistsCreditCard(BigInteger documentNumber) {
    return this.findCreditCards(documentNumber)
      .hasElements()
      .doOnSuccess(result -> log.info("Successful find exists Credit Card - documentNumber: "
        .concat(documentNumber.toString())));

  }
}
