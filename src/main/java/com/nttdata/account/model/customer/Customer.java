package com.nttdata.account.model.customer;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class: Customer. <br/>
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
public class Customer {

  private String id;
  private String type;
  private String status;
  private Address address;
  private IdentificationDocument identificationDocument;
  private PersonalInfo personalInfo;
  private BusinessInfo businessInfo;
  private LocalDateTime dateCreated;
  private LocalDateTime lastUpdated;

}

