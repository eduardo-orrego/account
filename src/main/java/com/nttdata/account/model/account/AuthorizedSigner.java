package com.nttdata.account.model.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nttdata.account.model.enums.SignerTypeEnum;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizedSigner {
    @JsonProperty(value = "signerId")
    private String signerId;

    @JsonProperty(value = "signerType")
    @Enumerated(EnumType.STRING)
    private SignerTypeEnum signerType;
}
