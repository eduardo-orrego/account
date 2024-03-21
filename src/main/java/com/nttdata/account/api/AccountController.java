package com.nttdata.account.api;

import com.nttdata.account.api.request.AccountRequest;
import com.nttdata.account.business.AccountService;
import com.nttdata.account.model.account.Account;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotNull;
import java.math.BigInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Class: AccountController. <br/>
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
@RestController
@RequestMapping(value = "/api/accounts")
public class AccountController {

  @Autowired
  private AccountService accountService;

  /**
   * POST : Create a new account.
   *
   * @param account (required)
   * @return Created (status code 201)
   */
  @Operation(
    operationId = "accountPost",
    summary = "Create a new account",
    responses = {
      @ApiResponse(responseCode = "201", description = "Created", content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = Account.class))
      })
    }
  )
  @PostMapping(
    value = "",
    produces = {"application/json"},
    consumes = {"application/json"}
  )
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Account> accountPost(
    @Validated @RequestBody(required = false) AccountRequest account
  ) {
    return accountService.saveAccount(account);
  }

  /**
   * PUT : Update an account exists.
   *
   * @param accountId (required)
   * @param account   (required)
   * @return Ok (status code 200)
   */
  @Operation(
    operationId = "accountPut",
    summary = "Update a account",
    responses = {
      @ApiResponse(responseCode = "200", description = "Updated", content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = Account.class))
      })
    }
  )
  @PutMapping(
    value = "/{accountId}",
    produces = {"application/json"},
    consumes = {"application/json"}
  )
  @ResponseStatus(HttpStatus.OK)
  public Mono<Account> accountPut(
    @Parameter(name = "accountId", description = "", required = true, in = ParameterIn.PATH)
    @PathVariable("accountId") String accountId,
    @Validated @RequestBody AccountRequest account
  ) {
    return accountService.updateAccount(account, accountId);
  }

  /**
   * GET /{accountNumber} : Get information about a specific account.
   *
   * @param accountNumber (required)
   * @return OK (status code 200)
   */
  @Operation(
    operationId = "accountsAccountNumberGet",
    summary = "Get information about a specific account",
    responses = {
      @ApiResponse(responseCode = "200", description = "OK", content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = Account.class))
      })
    }
  )
  @GetMapping(
    value = "/{accountNumber}",
    produces = {"application/json"}
  )
  @ResponseStatus(HttpStatus.OK)
  public Mono<Account> accountsAccountNumberGet(
    @Parameter(name = "accountNumber", description = "", required = true, in = ParameterIn.PATH)
    @PathVariable("accountNumber") BigInteger accountNumber
  ) {
    return accountService.getAccount(accountNumber);
  }

  /**
   * GET : Get a list of accounts for the account customer.
   *
   * @param customerDocument (required)
   * @return OK (status code 200)
   */
  @Operation(
    operationId = "accountsGet",
    summary = "Get a list of accounts for the customer",
    responses = {
      @ApiResponse(responseCode = "200", description = "OK", content = {
        @Content(mediaType = "application/json",
          array = @ArraySchema(schema = @Schema(implementation = Account.class)))
      })
    }
  )
  @GetMapping(
    value = "",
    produces = {"application/json"}
  )
  @ResponseStatus(HttpStatus.OK)
  public Flux<Account> accountsGet(
    @NotNull @Parameter(name = "customerDocument", description = "", required = true,
      in = ParameterIn.QUERY)
    @Validated @RequestParam(value = "customerDocument") BigInteger customerDocument
  ) {
    return accountService.getAccounts(customerDocument);
  }

}
