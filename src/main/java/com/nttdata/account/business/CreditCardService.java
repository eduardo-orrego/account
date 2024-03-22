package com.nttdata.account.business;

import com.nttdata.account.model.CreditCard;
import java.math.BigInteger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Class: CreditCardService. <br/>
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
public interface CreditCardService {
  Flux<CreditCard> findCreditCards(BigInteger documentNumber);
}
