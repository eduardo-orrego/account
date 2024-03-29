package com.nttdata.account.business.impl;

import com.nttdata.account.business.CreditCardService;
import com.nttdata.account.client.CreditCardClient;
import com.nttdata.account.model.CreditCard;
import java.math.BigInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

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
      .doOnComplete(() -> log.info(String.format("Successful find Credit Card - documentNumber: %s",
        documentNumber)));
  }
}
