package com.nttdata.account.model.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckingInfo {

    @JsonProperty(value = "minimumBalance")
    private BigDecimal minimumBalance;

    @JsonProperty(value = "checkbookNumber")
    private BigDecimal checkbookNumber;

    @JsonProperty(value = "numberChecks")
    private Integer numberChecks;

    @JsonProperty(value = "maintenanceFee")
    private BigDecimal maintenanceFee;

}
