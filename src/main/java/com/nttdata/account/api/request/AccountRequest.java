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

    @NotNull(message = "El campo 'accountNumber' no puede ser nulo")
    private BigInteger accountNumber;

    @NotNull(message = "El campo 'type' no puede ser nulo")
    private AccountTypeEnum type;

    @NotNull(message = "El campo 'status' no puede ser nulo")
    private StatusTypeEnum status;

    @NotNull(message = "El campo 'openingDate' no puede ser nulo")
    private LocalDateTime openingDate;

    @NotNull(message = "El campo 'availableBalance' no puede ser nulo")
    private BigDecimal availableBalance;

    @NotNull(message = "El campo 'interestRate' no puede ser nulo")
    private BigDecimal interestRate;

    @NotNull(message = "El campo 'maintenanceCommission' no puede ser nulo")
    private BigDecimal maintenanceCommission;

    @NotNull(message = "El campo 'monthlyLimitMovement' no puede ser nulo")
    private Integer monthlyLimitMovement;

    @NotNull(message = "El campo 'limitFreeMovements' no puede ser nulo")
    private Integer limitFreeMovements;

    @NotNull(message = "El campo 'commissionMovement' no puede ser nulo")
    private BigDecimal commissionMovement;

    @NotNull(message = "El campo 'specificDayMonthMovement' no puede ser nulo")
    private Integer specificDayMonthMovement;

    @NotNull(message = "El campo 'currency' no puede ser nulo")
    private CurrencyTypeEnum currency;

    @NotNull(message = "El campo 'customerId' no puede ser nulo")
    private String customerId;

    @Valid
    private List<AccountHolderRequest> accountHolders;

    @Valid
    private List<AuthorizedSignerRequest> authorizedSigners;

}