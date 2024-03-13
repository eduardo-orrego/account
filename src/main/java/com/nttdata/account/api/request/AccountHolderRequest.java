package com.nttdata.account.api.request;

import com.nttdata.account.enums.HolderTypeEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountHolderRequest {

    @NotNull(message = "El campo 'holderId' no puede ser vacío")
    private String holderId;

    @NotNull(message = "El campo 'holderType' no puede ser nulo")
    private HolderTypeEnum holderType;

}
