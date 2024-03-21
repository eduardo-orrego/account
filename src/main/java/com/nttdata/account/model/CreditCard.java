package com.nttdata.account.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class: CreditCard. <br/>
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
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditCard {

  private String id;
  private BigInteger cardNumber;
  private String status;
  private LocalDate expirationDate;
  private LocalDate activateDate;
  private Integer cvv;
  private BigInteger customerDocument;
  private BigInteger creditNumber;
  private LocalDateTime dateCreated;
  private LocalDateTime lastUpdated;

}
