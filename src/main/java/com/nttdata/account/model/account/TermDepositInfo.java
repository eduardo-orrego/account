package com.nttdata.account.model.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TermDepositInfo {

    @JsonProperty(value = "termLength")
    private Integer termLength;

    @JsonProperty(value = "maturityDate")
    private LocalDate maturityDate;

    @JsonProperty(value = "dayMonthMovement")
    private Integer dayMonthMovement;

}
