package com.nttdata.account.api;

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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * POST : Create a new account
     *
     * @param account (optional)
     * @return Created (status code 201)
     */
    @Operation(
        operationId = "accountsPost",
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
    public Mono<Account> accountsPost(
        @Parameter(name = "account", description = "")
        @Validated @RequestBody(required = false) Account account
    ) {
        return accountService.saveAccount(account);
    }

    /**
     * GET /{accountNumber} : Get information about a specific account
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
    public Mono<Account> accountsAccountNumberGet(
        @Parameter(name = "accountNumber", description = "", required = true, in = ParameterIn.PATH)
        @PathVariable("accountNumber") BigInteger accountNumber
    ) {
        return accountService.getAccountByAccountNumber(accountNumber);
    }


    /**
     * GET : Get a list of accounts for the account holder
     *
     * @param holderId (required)
     * @return OK (status code 200)
     */
    @Operation(
        operationId = "accountsGet",
        summary = "Get a list of accounts for the user",
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Account.class)))
            })
        }
    )
    @GetMapping(
        value = "",
        produces = {"application/json"}
    )
    public Flux<Account> accountsGet(
        @NotNull @Parameter(name = "holderId", description = "", required = true, in = ParameterIn.QUERY)
        @Validated @RequestParam(value = "holderId") String holderId
    ) {
        return accountService.getAccountsByHolderId(holderId);
    }

    /**
     * PUT : Update an account exists
     *
     * @param account (optional)
     * @return Ok (status code 200)
     */
    @Operation(
        operationId = "accountPut",
        summary = "Create a new account",
        responses = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Account.class))
            })
        }
    )
    @PutMapping(
        value = "",
        produces = {"application/json"},
        consumes = {"application/json"}
    )
    public Mono<Account> accountPut(
        @Parameter(name = "account", description = "")
        @Validated @RequestBody Account account
    ) {
        return accountService.updateAccount(account);
    }


}
