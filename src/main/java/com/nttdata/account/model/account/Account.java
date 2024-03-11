package com.nttdata.account.model.account;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "account")
public class Account {

    @Id
    private String id;
    private BigInteger accountNumber;
    private String type;
    private String status;
    private LocalDateTime openingDate;
    private BigDecimal availableBalance;
    private BigDecimal interestRate;
    private BigDecimal maintenanceCommission;
    private Integer monthlyLimitMovement;
    private Integer limitFreeMovements;
    private BigDecimal commissionMovement;
    private Integer specificDayMonthMovement;
    private String currency;
    private String customerId;
    private List<AccountHolder> accountHolders;
    private List<AuthorizedSigner> authorizedSigners;
    private LocalDateTime dateCreated;
    private LocalDateTime lastUpdated;
}