package com.nttdata.account.api.request;

import com.nttdata.account.api.constraint.ValidAccount;
import com.nttdata.account.enums.AccountTypeEnum;
import com.nttdata.account.enums.CurrencyTypeEnum;
import com.nttdata.account.enums.StatusTypeEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ValidAccount
public class AccountRequest {

    @NotNull(message = "El campo 'type' no puede ser nulo")
    private AccountTypeEnum type;

    @NotNull(message = "El campo 'status' no puede ser nulo")
    private StatusTypeEnum status;

    private BigInteger accountNumber;

    @NotNull(message = "El campo 'openingDate' no puede ser nulo")
    private LocalDateTime openingDate;

    @NotNull(message = "El campo 'availableBalance' no puede ser nulo")
    private BigDecimal availableBalance;

    private BigDecimal interestRate;
    private BigDecimal maintenanceCommission;
    private Integer monthlyLimitMovement;
    private Integer limitFreeMovements;
    private BigDecimal commissionMovement;
    private Integer specificDayMonthMovement;

    @NotNull(message = "El campo 'customerId' no puede ser nulo")
    private String customerId;

    @NotNull(message = "El campo 'currency' no puede ser nulo")
    private CurrencyTypeEnum currency;

    @Valid
    private List<AccountHolderRequest> accountHolders;

    @Valid
    private List<AuthorizedSignerRequest> authorizedSigners;

}