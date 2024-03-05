package com.nttdata.account.model.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nttdata.account.model.enums.AccountTypeEnum;
import com.nttdata.account.model.enums.CurrencyTypeEnum;
import com.nttdata.account.model.enums.StatusTypeEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "account")
public class Account {

    @Id
    private String id;

    @JsonProperty(value = "type")
    @Enumerated(EnumType.STRING)
    private AccountTypeEnum type;

    @JsonProperty(value = "status")
    @Enumerated(EnumType.STRING)
    private StatusTypeEnum status;

    @JsonProperty(value = "accountNumber")
    private BigInteger accountNumber;

    @JsonProperty(value = "openingDate")
    private LocalDateTime openingDate;

    @JsonProperty(value = "availableBalance")
    private BigDecimal availableBalance;

    @JsonProperty(value = "currency")
    @Enumerated(EnumType.STRING)
    private CurrencyTypeEnum currency;

    @NotEmpty
    @JsonProperty(value = "accountHolders")
    private List<AccountHolder> accountHolders;

    @NotNull
    @JsonProperty(value = "authorizedSigners")
    private List<AuthorizedSigner> authorizedSigners;

    @JsonProperty(value = "savingsInfo")
    private SavingsInfo savingsInfo;

    @JsonProperty(value = "termDepositInfo")
    private TermDepositInfo termDepositInfo;

    @JsonProperty(value = "checkingInfo")
    private CheckingInfo checkingInfo;

    @JsonProperty(value = "dateCreated")
    private LocalDateTime dateCreated;

    @JsonProperty(value = "lastUpdated")
    private LocalDateTime lastUpdated;
}