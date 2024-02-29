package com.nttdata.account.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavingsInfo {
    @JsonProperty(value = "savings_goal")
    private String savingsGoal;

    @JsonProperty(value = "interest_rate")
    private BigDecimal interestRate;

    @JsonProperty(value = "monthly movements")
    private BigDecimal monthlyMovements;

    @JsonProperty(value = "maintenance_fee")
    private BigDecimal maintenanceFee;

}
