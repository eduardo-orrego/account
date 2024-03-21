package com.nttdata.account.builder;

import com.nttdata.account.api.request.AccountHolderRequest;
import com.nttdata.account.api.request.AccountRequest;
import com.nttdata.account.api.request.AuthorizedSignerRequest;
import com.nttdata.account.model.Product;
import com.nttdata.account.model.account.Account;
import com.nttdata.account.model.account.AccountHolder;
import com.nttdata.account.model.account.AuthorizedSigner;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

/**
 * Class: AccountBuilder. <br/>
 * <b>Bootcamp NTTDATA</b><br/>
 *
 * @author NTTDATA
 * @version 1.0
 *   <u>Developed by</u>:
 *   <ul>
 *   <li>Developer Carlos</li>
 *   </ul>
 * @since 1.0
 */
public class AccountBuilder {
  AccountBuilder() {
  }

  public static Account toEntity(AccountRequest accountRequest, Product product) {
    return Account.builder()
      .accountNumber(generateDebitNumber())
      .type(accountRequest.getType().name())
      .status(accountRequest.getStatus().name())
      .openingDate(accountRequest.getOpeningDate())
      .openingAmount(accountRequest.getAmount())
      .currency(accountRequest.getCurrency().name())
      .availableBalance(accountRequest.getAmount())
      .interestRate(Objects.nonNull(accountRequest.getInterestRate())
        ? accountRequest.getInterestRate()
        : product.getInterestRate())
      .maintenanceCommission(Objects.nonNull(accountRequest.getMaintenanceCommission())
        ? accountRequest.getMaintenanceCommission()
        : product.getMaintenanceCommission())
      .monthlyLimitMovement(Objects.nonNull(accountRequest.getMonthlyLimitMovement())
        ? accountRequest.getMonthlyLimitMovement()
        : product.getMonthlyLimitMovement())
      .limitFreeMovements(Objects.nonNull(accountRequest.getLimitFreeMovements())
        ? accountRequest.getLimitFreeMovements()
        : product.getLimitFreeMovements())
      .commissionMovement(Objects.nonNull(accountRequest.getCommissionMovement())
        ? accountRequest.getCommissionMovement()
        : product.getCommissionMovement())
      .specificDayMonthMovement(Objects.nonNull(accountRequest.getSpecificDayMonthMovement())
        ? accountRequest.getSpecificDayMonthMovement()
        : product.getSpecificDayMonthMovement())
      .accountHolders(toAccountHolderEntities(accountRequest.getAccountHolders(), null))
      .authorizedSigners(toAuthorizedSignerEntities(accountRequest.getAuthorizedSigners(), null))
      .lastTransactionDate(LocalDateTime.now())
      .dateCreated(LocalDateTime.now())
      .lastUpdated(LocalDateTime.now())
      .build();
  }

  public static Account toEntity(AccountRequest accountRequest, Account account) {
    return Account.builder()
      .id(account.getId())
      .accountNumber(account.getAccountNumber())
      .type(accountRequest.getType().name())
      .status(accountRequest.getStatus().name())
      .openingDate(accountRequest.getOpeningDate())
      .openingAmount(accountRequest.getAmount())
      .currency(accountRequest.getCurrency().name())
      .availableBalance(Objects.nonNull(accountRequest.getAvailableBalance())
        ? accountRequest.getAvailableBalance()
        : account.getAvailableBalance())
      .interestRate(Objects.nonNull(accountRequest.getInterestRate())
        ? accountRequest.getInterestRate()
        : account.getInterestRate())
      .maintenanceCommission(Objects.nonNull(accountRequest.getMaintenanceCommission())
        ? accountRequest.getMaintenanceCommission()
        : account.getMaintenanceCommission())
      .monthlyLimitMovement(Objects.nonNull(accountRequest.getMonthlyLimitMovement())
        ? accountRequest.getMonthlyLimitMovement()
        : account.getMonthlyLimitMovement())
      .limitFreeMovements(Objects.nonNull(accountRequest.getLimitFreeMovements())
        ? accountRequest.getLimitFreeMovements()
        : account.getLimitFreeMovements())
      .commissionMovement(Objects.nonNull(accountRequest.getCommissionMovement())
        ? accountRequest.getCommissionMovement()
        : account.getCommissionMovement())
      .specificDayMonthMovement(Objects.nonNull(accountRequest.getSpecificDayMonthMovement())
        ? accountRequest.getSpecificDayMonthMovement()
        : account.getSpecificDayMonthMovement())
      .accountHolders(
        toAccountHolderEntities(accountRequest.getAccountHolders(), account.getAccountHolders()))
      .authorizedSigners(toAuthorizedSignerEntities(accountRequest.getAuthorizedSigners(),
        account.getAuthorizedSigners()))
      .lastTransactionDate(account.getLastTransactionDate())
      .dateCreated(account.getDateCreated())
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
      .customerDocument(accountHolderRequest.getCustomerDocument())
      .holderType(accountHolderRequest.getHolderType().name())
      .build();
  }

  private static List<AccountHolder> toAccountHolderEntities(
    List<AccountHolderRequest> accountHolderRequestList,
    List<AccountHolder> accountHolderListCurrent) {

    return Objects.nonNull(accountHolderRequestList) && !accountHolderRequestList.isEmpty()
      ? accountHolderRequestList.stream().map(AccountBuilder::toAccountHolderEntity).toList()
      : accountHolderListCurrent;
  }


  private static AuthorizedSigner toAuthorizedSignerEntity(
    AuthorizedSignerRequest authorizedSignerRequest) {
    return AuthorizedSigner.builder()
      .customerDocument(authorizedSignerRequest.getCustomerDocument())
      .signerType(authorizedSignerRequest.getSignerType().name())
      .build();
  }

  private static List<AuthorizedSigner> toAuthorizedSignerEntities(
    List<AuthorizedSignerRequest> authorizedSignerRequestList,
    List<AuthorizedSigner> accountSingerListCurrent) {
    return Objects.nonNull(authorizedSignerRequestList) && !authorizedSignerRequestList.isEmpty()
      ? authorizedSignerRequestList.stream().map(AccountBuilder::toAuthorizedSignerEntity).toList()
      : accountSingerListCurrent;
  }
}
