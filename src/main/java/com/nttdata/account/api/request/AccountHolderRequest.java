package com.nttdata.account.api.request;

import com.nttdata.account.enums.HolderTypeEnum;
import jakarta.validation.constraints.NotNull;
import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountHolderRequest {

    @NotNull(message = "El campo 'customerDocument' no puede ser vacío")
    private BigInteger customerDocument;

    @NotNull(message = "El campo 'holderType' no puede ser nulo")
    private HolderTypeEnum holderType;

}
