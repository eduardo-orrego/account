package com.nttdata.account.api.request;

import com.nttdata.account.enums.HolderTypeEnum;
import jakarta.validation.constraints.NotNull;
import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class: AccountHolderRequest. <br/>
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
public class AccountHolderRequest {

  @NotNull(message = "El campo 'customerDocument' no puede ser vacío")
  private BigInteger customerDocument;

  @NotNull(message = "El campo 'holderType' no puede ser nulo")
  private HolderTypeEnum holderType;

}
