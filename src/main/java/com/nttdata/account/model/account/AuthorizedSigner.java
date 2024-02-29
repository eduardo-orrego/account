package com.nttdata.account.model.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nttdata.account.model.enums.SignerTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizedSigner {
    @JsonProperty(value = "signer_id")
    private String signerId;

    @JsonProperty(value = "signer_type")
    private SignerTypeEnum signerType;
}
