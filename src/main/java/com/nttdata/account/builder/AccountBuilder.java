package com.nttdata.account.builder;

import com.nttdata.account.api.request.AccountHolderRequest;
import com.nttdata.account.api.request.AccountRequest;
import com.nttdata.account.api.request.AuthorizedSignerRequest;
import com.nttdata.account.model.account.Account;
import com.nttdata.account.model.account.AccountHolder;
import com.nttdata.account.model.account.AuthorizedSigner;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class AccountBuilder {
    AccountBuilder() {
    }

    public static Account toEntity(AccountRequest accountRequest, String accountId) {
        return Account.builder()
            .id(accountId)
            .accountNumber(accountRequest.getAccountNumber())
            .type(accountRequest.getType().name())
            .status(accountRequest.getStatus().name())
            .openingDate(accountRequest.getOpeningDate())
            .availableBalance(accountRequest.getAvailableBalance())
            .interestRate(accountRequest.getInterestRate())
            .maintenanceCommission(accountRequest.getMaintenanceCommission())
            .monthlyLimitMovement(accountRequest.getMonthlyLimitMovement())
            .limitFreeMovements(accountRequest.getLimitFreeMovements())
            .commissionMovement(accountRequest.getCommissionMovement())
            .specificDayMonthMovement(accountRequest.getSpecificDayMonthMovement())
            .currency(accountRequest.getCurrency().name())
            .customerId(accountRequest.getCustomerId())
            .accountHolders(toAccountHolderEntities(accountRequest.getAccountHolders()))
            .authorizedSigners(toAuthorizedSignerEntities(accountRequest.getAuthorizedSigners()))
            .dateCreated(LocalDateTime.now())
            .lastUpdated(LocalDateTime.now())
            .build();
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
