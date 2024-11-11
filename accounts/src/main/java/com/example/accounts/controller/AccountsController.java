package com.example.accounts.controller;

import com.example.accounts.domain.BankAccount;
import com.example.accounts.domain.Client;
import com.example.accounts.domain.PersonInfo;
import com.example.accounts.service.BankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class AccountsController {

    @Autowired
    private BankService bankService;


    @QueryMapping
    public List<BankAccount> accounts() {
        log.info("START - Getting all accounts");
        List<BankAccount> accounts = bankService.getAccounts();
        log.info("END - Getting all accounts");
        return accounts;
    }


    /**
     * Get Clients with N+1 Problem
     */
    @SchemaMapping(typeName = "BankAccount", field = "client")
    Client getClient(BankAccount account) {
        log.info("Getting client for " + account.getId());
        return bankService.getClientByAccountId(account.getId());
    }


    /**
     * Get Clients without N+1 Problem
     *
     * @BatchMapping(field = "client")
     * Map<BankAccount, Client> getClient(List<BankAccount> accounts) {
     * log.info("Getting client for accounts: " + accounts.size());
     * return bankService.getClientsforBankAccounts(accounts);
     * }
     */


    // Get PersonInfo with N+1 Problem
    @SchemaMapping(typeName = "Client", field = "person")
    PersonInfo getPersonInfo(Client client) {
        log.info("Getting person for client: " + client.getId());
        return bankService.getPersonInfoByClientId(client.getId());
    }

/**
 //    Get PersonInfo without N+1 Problem
 @BatchMapping(field = "person")
 Map<Client, PersonInfo> getPersonInfo(List<Client> clients) {
 log.info("Getting person for client: " + clients.size());
 return bankService.getPersonInfoforClients(clients);
 }
 */
}
