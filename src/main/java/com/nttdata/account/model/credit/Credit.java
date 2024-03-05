package com.nttdata.account.model.credit;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "credit")
public class Credit {

    @JsonProperty(value = "id")
    private String id;

    @JsonProperty(value = "type")
    private String type;

    @JsonProperty(value = "status")
    private String status;

    @JsonProperty(value = "creditNumber")
    private BigInteger creditNumber;

    @JsonProperty(value = "outstandingBalance")
    private BigDecimal outstandingBalance;

    @JsonProperty(value = "openingDate")
    private LocalDate openingDate;

    @JsonProperty(value = "lastTransactionDate")
    private LocalDate lastTransactionDate;

    @JsonProperty(value = "currency")
    private String currency;

    @JsonProperty(value = "interestRate")
    private BigDecimal interestRate;

    @JsonProperty(value = "creditHolders")
    private List<CreditHolder> creditHolders;

    @JsonProperty(value = "authorizedSigners")
    private List<AuthorizedSigner> authorizedSigners;

    @JsonProperty(value = "loanInfo")
    private LoanInfo loanInfo;

    @JsonProperty(value = "creditCardInfo")
    private CreditCardInfo creditCardInfo;

    @JsonProperty(value = "dateCreated")
    private LocalDateTime dateCreated;

    @JsonProperty(value = "lastUpdated")
    private LocalDateTime lastUpdated;

}
