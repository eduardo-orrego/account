package com.nttdata.account.model.credit;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardInfo {

    @JsonProperty(value = "creditLimit")
    private BigDecimal creditLimit;

    @JsonProperty(value = "availableBalance")
    private BigDecimal availableBalance;

    @JsonProperty(value = "cardNumber")
    private String cardNumber;

    @JsonProperty(value = "expirationDate")
    private LocalDate expirationDate;

    @JsonProperty(value = "activateDate")
    private LocalDate activateDate;

    @JsonProperty(value = "cvv")
    @Size(min = 3, max = 3)
    private String cvv;

    @JsonProperty(value = "holderId")
    private String holderId;

}