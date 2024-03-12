package com.nttdata.account.model;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String id;
    private String type;
    private String currency;
    private BigDecimal interestRate;
    private BigDecimal maintenanceCommission;
    private Integer monthlyLimitMovement;
    private Integer limitFreeMovements;
    private BigDecimal commissionMovement;
    private Integer specificDayMonthMovement;
    private LocalDateTime dateCreated;
    private LocalDateTime lastUpdated;
}
