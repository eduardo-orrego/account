package com.nttdata.account.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nttdata.account.model.enums.AccountTypeEnum;
import com.nttdata.account.model.enums.CurrencyTypeEnum;
import com.nttdata.account.model.enums.StatusTypeEnum;
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
    private AccountTypeEnum accountType;

    @JsonProperty(value = "status")
    @Enumerated(EnumType.STRING)
    private StatusTypeEnum status;

    @JsonProperty(value = "account_number")
    private BigInteger accountNumber;

    @JsonProperty(value = "opening_date")
    private LocalDateTime openingDate;

    @JsonProperty(value = "available_balance")
    private BigDecimal availableBalance;

    @JsonProperty(value = "currency")
    private CurrencyTypeEnum currency;

    @JsonProperty(value = "account_holders")
    private List<AccountHolder> accountHolders;

    @JsonProperty(value = "authorized_signers")
    private List<AuthorizedSigner> authorizedSigners;

    @JsonProperty(value = "savings_info")
    private SavingsInfo savingsInfo;

    @JsonProperty(value = "term_deposit_info")
    private TermDepositInfo termDepositInfo;

    @JsonProperty(value = "checking_info")
    private CheckingInfo checkingInfo;

    @JsonProperty(value = "date_created")
    private LocalDateTime dateCreated;

    @JsonProperty(value = "last_updated")
    private LocalDateTime lastUpdated;
}