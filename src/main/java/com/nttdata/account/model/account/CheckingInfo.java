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

    @JsonProperty(value = "minimum_balance")
    private BigDecimal minimumBalance;

    @JsonProperty(value = "checkbook_number")
    private BigDecimal checkbookNumber;

    @JsonProperty(value = "number_checks")
    private Integer numberChecks;

    @JsonProperty(value = "maintenance_fee")
    private BigDecimal maintenanceFee;

}
