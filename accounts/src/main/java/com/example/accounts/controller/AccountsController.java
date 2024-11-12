package com.example.accounts.controller;

import com.example.accounts.domain.Client;
import com.example.accounts.domain.Person;
import com.example.accounts.entity.BankAccount;
import com.example.accounts.exception.ClientNotFoundException;
import com.example.accounts.service.BankService;
import graphql.GraphQLError;
import graphql.schema.DataFetchingEnvironment;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class AccountsController {

    @Autowired
    private BankService bankService;

    @QueryMapping
    public List<BankAccount> accounts(@ContextValue(required = false) String accountStatus) {
        log.info("START - Getting all accounts");
        List<BankAccount> accounts = bankService.getAccounts(accountStatus);
        return accounts;
    }

    @QueryMapping
    public BankAccount accountById(@Argument("accountId") Long accountId) {
        log.info("Getting Account ");
        return bankService.accountById(accountId);
    }

    @BatchMapping(field = "client", typeName = "BankAccountType")
    public Map<BankAccount, Client> getClient(List<BankAccount> accounts) {
        log.info("Getting client for accounts: " + accounts.size());
        return bankService.getClientsforBankAccounts(accounts);
    }

    @BatchMapping(field = "person", typeName = "ClientType")
    public Map<Client, Person> getPersonInfo(List<Client> clients) {
        log.info("Getting person for client: " + clients.size());
        return bankService.getPersonInfoforClients(clients);
    }

    @MutationMapping
    public Boolean addAccount(@Argument("account") BankAccount account){
        log.info("Adding BankAccount");
        return bankService.save(account);
    }

    @MutationMapping
    public BankAccount editAccount(@Argument("account") BankAccount account){
        log.info("Editing BankAccount");
        return bankService.modify(account);
    }

    @MutationMapping
    public Boolean deleteAccount(@Argument("accountId") Long accountId){
        log.info("Deleting BankAccountId:" + accountId);
        return bankService.remove(accountId);
    }

    @GraphQlExceptionHandler
    public GraphQLError handle(@NonNull ClientNotFoundException ex, @NonNull DataFetchingEnvironment environment) {
        return GraphQLError
                .newError()
                .errorType(ErrorType.BAD_REQUEST)
                .message("Unable to locate the specified client. " +
                        "Please verify the client details and attempt your request again. : "
                        + ex.getMessage())
                .path(environment.getExecutionStepInfo().getPath())
                .location(environment.getField().getSourceLocation())
                .build();
    }
}
