package com.nttdata.account.model.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavingsInfo {

    @JsonProperty(value = "savingsGoal")
    private String savingsGoal;

    @JsonProperty(value = "interestRate")
    private BigDecimal interestRate;

    @JsonProperty(value = "monthlyMovements")
    private Integer monthlyMovements;

    @JsonProperty(value = "numberFreeTransactions")
    private Integer numberFreeTransactions;

    @JsonProperty(value = "transactionFee")
    private BigDecimal transactionFee;

}
