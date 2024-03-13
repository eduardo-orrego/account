package com.nttdata.account.builder;

import com.nttdata.account.api.request.AccountHolderRequest;
import com.nttdata.account.api.request.AccountRequest;
import com.nttdata.account.api.request.AuthorizedSignerRequest;
import com.nttdata.account.model.account.Account;
import com.nttdata.account.model.account.AccountHolder;
import com.nttdata.account.model.account.AuthorizedSigner;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class AccountBuilder {
    AccountBuilder() {
    }

    public static Account toEntity(AccountRequest accountRequest, String accountId) {
        return Account.builder()
            .id(accountId)
            .accountNumber(Objects.nonNull(accountRequest.getAccountNumber())
                ? accountRequest.getAccountNumber()
                : generateDebitNumber())
            .type(accountRequest.getType().name())
            .status(accountRequest.getStatus().name())
            .openingDate(accountRequest.getOpeningDate())
            .availableBalance(Objects.nonNull(accountRequest.getAvailableBalance())
                ? accountRequest.getAvailableBalance()
                : BigDecimal.valueOf(0.00))
            .interestRate(Objects.nonNull(accountRequest.getInterestRate())
                ? accountRequest.getInterestRate()
                : BigDecimal.valueOf(0.00))
            .maintenanceCommission(Objects.nonNull(accountRequest.getMaintenanceCommission())
                ? accountRequest.getMaintenanceCommission()
                : BigDecimal.valueOf(0.00))
            .monthlyLimitMovement(Objects.nonNull(accountRequest.getMonthlyLimitMovement())
                ? accountRequest.getMonthlyLimitMovement()
                : 0)
            .limitFreeMovements(Objects.nonNull(accountRequest.getLimitFreeMovements())
                ? accountRequest.getLimitFreeMovements()
                : 0)
            .commissionMovement(Objects.nonNull(accountRequest.getCommissionMovement())
                ? accountRequest.getCommissionMovement()
                : BigDecimal.valueOf(0.00))
            .specificDayMonthMovement(Objects.nonNull(accountRequest.getSpecificDayMonthMovement())
                ? accountRequest.getSpecificDayMonthMovement()
                : 0)
            .currency(accountRequest.getCurrency().name())
            .customerId(accountRequest.getCustomerId())
            .accountHolders(toAccountHolderEntities(accountRequest.getAccountHolders()))
            .authorizedSigners(toAuthorizedSignerEntities(accountRequest.getAuthorizedSigners()))
            .lastTransactionDate(LocalDateTime.now())
            .dateCreated(LocalDateTime.now())
            .lastUpdated(LocalDateTime.now())
            .build();
    }

    private static BigInteger generateDebitNumber() {
        String accountNumber = "10".concat(
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));
        return new BigInteger(accountNumber);
    }

    private static AccountHolder toAccountHolderEntity(AccountHolderRequest accountHolderRequest) {
        return AccountHolder.builder()
            .holderId(accountHolderRequest.getHolderId())
            .holderType(accountHolderRequest.getHolderType().name())
            .build();
    }

    private static List<AccountHolder> toAccountHolderEntities(List<AccountHolderRequest> accountHolderRequestList) {

        return Objects.nonNull(accountHolderRequestList) && !accountHolderRequestList.isEmpty()
            ? accountHolderRequestList.stream().map(AccountBuilder::toAccountHolderEntity).toList()
            : null;
    }

    private static AuthorizedSigner toAuthorizedSignerEntity(AuthorizedSignerRequest authorizedSignerRequest) {
        return AuthorizedSigner.builder()
            .signerId(authorizedSignerRequest.getSignerId())
            .signerType(authorizedSignerRequest.getSignerType().name())
            .build();
    }

    private static List<AuthorizedSigner> toAuthorizedSignerEntities(
        List<AuthorizedSignerRequest> authorizedSignerRequestList) {
        return Objects.nonNull(authorizedSignerRequestList) && !authorizedSignerRequestList.isEmpty()
            ? authorizedSignerRequestList.stream().map(AccountBuilder::toAuthorizedSignerEntity).toList()
            : null;
    }
}
