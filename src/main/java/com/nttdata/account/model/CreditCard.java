package com.nttdata.account.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCard {

    private String id;
    private BigInteger cardNumber;
    private String status;
    private LocalDate expirationDate;
    private LocalDate activateDate;
    private String cvv;
    private String customerId;
    private BigDecimal interestRate;
    private BigDecimal availableBalance;
    private BigDecimal creditLimit;
    private LocalDateTime lastTransactionDate;
    private LocalDateTime dateCreated;
    private LocalDateTime lastUpdated;

}
