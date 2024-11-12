package com.example.accounts.service;

import com.example.accounts.domain.Client;
import com.example.accounts.domain.Person;
import com.example.accounts.entity.BankAccount;
import com.example.accounts.exception.AccountNotFoundException;
import com.example.accounts.exception.ClientNotFoundException;
import com.example.accounts.repository.BankAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class BankService {

    @Autowired
    BankAccountRepository repository;

    private static List<Client> clients = Arrays.asList(
            new Client(2001L, 3001L,"IN"),
            new Client(2002L, 3002L,"IN"),
            new Client(2003L, 3003L,"IN"),
            new Client(2004L, 3004L,"IN"),
            new Client(2005L, 3005L,"IN")
    );

    private static List<Person> persons = Arrays.asList(
            new Person(3001L, 2001L, "Elena", "Maria", "Gonzalez"),
            new Person(3002L, 2002L, "James", "Robert", "Smith"),
            new Person(3003L, 2003L, "Aarav", "Kumar", "Patel"),
            new Person(3004L, 2004L, "Linh", "Thi", "Nguyen"),
            new Person(3005L, 2005L, "Olivia", "Grace", "Johnson")
    );

    public List<BankAccount> getAccounts() {
        return repository.findAll();
    }

    public List<Person> getPersons() {
        return persons;
    }

    public List<Client> getClients() {
        return clients;
    }


    public Person getPersonInfoByClientId(String clientId) {
        return persons.stream().filter(c -> c.getClientId().equals(clientId)).findFirst().orElse(null);
    }


    public Map<BankAccount, Client> getClientsforBankAccounts(List<BankAccount> accs) {
        Map<BankAccount, Client> clientsForAccount = new HashMap<>();

        Client cl;
        for (BankAccount acc : accs) {
            cl = clients.stream().filter(c -> c.getClientId().equals(acc.getClientId())).findFirst().orElse(null);
            clientsForAccount.put(acc, cl);

        }
        return clientsForAccount;
    }

    /**
     * public Map<BankAccount, Client> getClientsforBankAccountsBck(List<BankAccount> accounts) {
     * return accounts.stream()
     * .collect(Collectors.toMap(
     * account -> account, // Key Mapper
     * account -> clients.stream()
     * .filter(c -> c.getId().equals(account.getClientId()))
     * .findFirst()
     * .orElse(null) // Value Mapper
     * ));
     * }
     */

    public Map<Client, Person> getPersonInfoforClients(List<Client> clients) {

        Map<Client, Person> personInfoForClients = new HashMap<>();
        ;
        Person pi;
        for (Client client : clients) {
            pi = persons.stream().filter(c -> c.getClientId().equals(client.getClientId())).findFirst().orElse(null);
            personInfoForClients.put(client, pi);

        }
        return personInfoForClients;
    }

    public BankAccount accountById(Long accountId) {
        if (repository.findById(accountId).isPresent()) {
            return repository.findById(accountId).get();
        }
        throw new AccountNotFoundException("Account Not Found");
    }

    public Boolean save(BankAccount account) {
        if (validClient(account)) {
            repository.save(account);
            log.info("added account:" + account.toString());
        } else {
            throw new ClientNotFoundException("Client Not Found");
        }
        return true;
    }

    public Boolean remove(Long accountId) {
        repository.delete(repository.findById(accountId).get());
        return true;
    }

    public BankAccount modify(BankAccount account) {
        if (validAccount(account) && validClient(account)) {
            repository.save(account);
        } else {
            throw new ClientNotFoundException("Client Not Found");
        }
        return account;
    }

    private boolean validClient(BankAccount account) {
        return getClients().stream().filter(c -> c.getClientId().equals(account.getClientId())).findAny().isPresent();
    }

    private boolean validAccount(BankAccount account) {
        return getAccounts().stream().filter(a->a.getAccountId().equals(account.getAccountId())).findAny().isPresent();
    }
}
