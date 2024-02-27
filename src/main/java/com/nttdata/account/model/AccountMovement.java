package com.nttdata.account.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nttdata.account.model.enums.AccountTransactionTypeEnum;
import com.nttdata.account.model.enums.CurrencyTypeEnum;
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
@Document(collection = "account_movement")
public class AccountMovement {

    @Id
    private String id;

    @JsonProperty(value = "transaction_type")
    @Enumerated(EnumType.STRING)
    private AccountTransactionTypeEnum transactionType;

    @JsonProperty(value = "transaction_date")
    private String transactionDate;

    @JsonProperty(value = "account_number")
    private String accountNumber;

    @JsonProperty(value = "amount")
    private String amount;

    @JsonProperty(value = "currency")
    @Enumerated(EnumType.STRING)
    private CurrencyTypeEnum currency;

}

