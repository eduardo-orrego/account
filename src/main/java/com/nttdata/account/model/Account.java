package com.nttdata.account.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nttdata.account.model.enums.AccountTypeEnum;
import jakarta.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.math.BigInteger;
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

    @JsonProperty(value = "account_type")
    @Enumerated(EnumType.STRING)
    private AccountTypeEnum accountType;

    @JsonProperty(value = "account_number")
    private BigInteger accountNumber;

    @JsonProperty(value = "available_balance")
    private BigDecimal availableBalance;

    @NotEmpty
    @JsonProperty(value = "account_holders")
    private List<AccountHolder> accountHolders;

    @JsonProperty(value = "authorized_signers")
    private List<AccountAuthorizedSigner> accountAuthorizedSigners;

    @JsonProperty(value = "maintenance_fee")
    private BigDecimal maintenanceFee;

    @JsonProperty(value = "account_movement_limit")
    private AccountMovementLimit accountMovementLimit;

}