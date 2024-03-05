package com.nttdata.account.model.credit;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanInfo {

    @JsonProperty(value = "amount")
    private BigDecimal amount;

    @JsonProperty(value = "term")
    private Integer term;

    @JsonProperty(value = "purpose")
    private String purpose;

    @JsonProperty(value = "maturityDate")
    private LocalDate maturityDate;

    @JsonProperty(value = "disbursementDate")
    @NotNull
    private LocalDate disbursementDate;

}
