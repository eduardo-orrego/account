package com.nttdata.account.api.request;

import com.nttdata.account.enums.SignerTypeEnum;
import jakarta.validation.constraints.NotNull;
import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class: AuthorizedSignerRequest. <br/>
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
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizedSignerRequest {

  @NotNull(message = "El campo 'customerDocument' no puede ser vacío")
  private BigInteger customerDocument;

  @NotNull(message = "El campo 'signerType' no puede ser nulo")
  private SignerTypeEnum signerType;
}
