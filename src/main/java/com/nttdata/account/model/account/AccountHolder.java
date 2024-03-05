package com.nttdata.account.model.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nttdata.account.model.enums.HolderTypeEnum;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountHolder {

    @JsonProperty(value = "holderId")
    private String holderId;

    @JsonProperty(value = "holderType")
    @Enumerated(EnumType.STRING)
    private HolderTypeEnum holderType;

}
