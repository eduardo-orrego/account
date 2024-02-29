package com.nttdata.account.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TermDepositInfo {

    @JsonProperty(value = "term_length")
    private Integer termLength;

    @JsonProperty(value = "maturity_date")
    private LocalDate maturityDate;

    @JsonProperty(value = "quantity_movements")
    private Integer quantityMovements;

    @JsonProperty(value = "day_month_movements")
    private Integer dayMonthMovements;
}
