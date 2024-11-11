package com.example.accounts.controller;

import com.example.accounts.domain.Client;
import com.example.accounts.domain.Person;
import com.example.accounts.entity.BankAccount;
import com.example.accounts.service.BankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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
}
