package com.nttdata.account.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountHolder {
    @JsonProperty(value = "holder_id")
    private String holderId;

    @JsonProperty(value = "holder_name")
    private String holder_name;
}
