package com.nttdata.account.api.request;

import com.nttdata.account.enums.SignerTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizedSignerRequest {
    private String signerId;

    private SignerTypeEnum signerType;
}
