package com.nttdata.account.api.request;

import com.nttdata.account.enums.AccountTypeEnum;
import com.nttdata.account.enums.CurrencyTypeEnum;
import com.nttdata.account.enums.StatusTypeEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {

    @NotNull(message = "El campo 'type' no puede ser nulo")
    private AccountTypeEnum type;

    @NotNull(message = "El campo 'status' no puede ser nulo")
    private StatusTypeEnum status;

    private BigInteger accountNumber;

    @NotNull(message = "El campo 'openingDate' no puede ser nulo")
    private LocalDate openingDate;

    @NotNull(message = "El campo 'availableBalance' no puede ser nulo")
    private BigDecimal availableBalance;

    private BigDecimal interestRate;
    private BigDecimal maintenanceCommission;
    private Integer monthlyLimitMovement;
    private Integer limitFreeMovements;
    private BigDecimal commissionMovement;
    private Integer specificDayMonthMovement;

    @NotNull(message = "El campo 'currency' no puede ser nulo")
    private CurrencyTypeEnum currency;

    @NotEmpty(message = "El campo accountHolders no puede estar vacío")
    @Valid
    private List<AccountHolderRequest> accountHolders;

    @Valid
    private List<AuthorizedSignerRequest> authorizedSigners;

}