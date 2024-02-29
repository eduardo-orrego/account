package com.nttdata.account.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizedSigner {
    @JsonProperty(value = "authorized_signer_id")
    private String authorizedSignerId;

    @JsonProperty(value = "authorized_signer_name")
    private String authorizedSignerName;
}
