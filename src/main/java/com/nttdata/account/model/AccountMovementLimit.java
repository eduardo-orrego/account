package com.nttdata.account.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nttdata.account.model.enums.MovementLimitTypeEnum;
import java.math.BigDecimal;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountMovementLimit {
    @JsonProperty(value = "maximum_limit")
    private BigDecimal maximumMovementLimit;

    @JsonProperty(value = "type_limit")
    @Enumerated(EnumType.STRING)
    private MovementLimitTypeEnum typeMovementLimit;

    @JsonProperty(value = "day_month")
    private Integer dayMonth;
}
