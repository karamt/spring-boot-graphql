package com.example.accounts.service;

import com.example.accounts.domain.BankAccount;
import com.example.accounts.domain.Client;
import com.example.accounts.domain.Currency;
import com.example.accounts.domain.PersonInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BankService {
    private static List<BankAccount> bankAccounts = Arrays.asList(
            new BankAccount("A100", "C100", Currency.USD, 106.00f, "A"),
            new BankAccount("A101", "C200", Currency.CAD, 250.00f, "A"),
            new BankAccount("A102", "C300", Currency.CAD, 333.00f, "I"),
            new BankAccount("A103", "C400", Currency.EUR, 4000.00f, "A"),
            new BankAccount("A104", "C500", Currency.EUR, 4000.00f, "A")
    );
    private static List<Client> clients = Arrays.asList(
            new Client("C100", "A100", "P100"),
            new Client("C200", "A101", "P200"),
            new Client("C300", "A102", "P300"),
            new Client("C400", "A103", "P400"),
            new Client("C500", "A104", "P500")
    );

    private static List<PersonInfo> persons = Arrays.asList(
            new PersonInfo("P100", "C100", "Elena", "Maria", "Gonzalez"),
            new PersonInfo("P200", "C200", "James", "Robert", "Smith"),
            new PersonInfo("P300", "C300", "Aarav", "Kumar", "Patel"),
            new PersonInfo("P400", "C400", "Linh", "Thi", "Nguyen"),
            new PersonInfo("P500", "C500", "Olivia", "Grace", "Johnson")
    );

    public List<BankAccount> getAccounts() {
        return bankAccounts;
    }

    public List<PersonInfo> getPersons() {
        return persons;
    }

    public Client getClientByAccountId(String accountId) {
        return clients.stream().filter(c -> c.getAccountId().equals(accountId)).findFirst().orElse(null);
    }

    public PersonInfo getPersonInfoByClientId(String clientId) {
        return persons.stream().filter(c -> c.getClientId().equals(clientId)).findFirst().orElse(null);
    }

    public Map<BankAccount, Client> getClientsforBankAccounts(List<BankAccount> accs) {
        Map<BankAccount, Client> clientsForAccount = new HashMap<>();

        Client cl;
        for (BankAccount acc : accs) {
            cl = clients.stream().filter(c -> c.getAccountId().equals(acc.getId())).findFirst().orElse(null);
            clientsForAccount.put(acc, cl);

        }
        return clientsForAccount;
    }

    public Map<BankAccount, Client> getClientsforBankAccountsBck(List<BankAccount> accounts) {
        return accounts.stream()
                .collect(Collectors.toMap(
                        account -> account, // Key Mapper
                        account -> clients.stream()
                                .filter(c -> c.getId().equals(account.getClientId()))
                                .findFirst()
                                .orElse(null) // Value Mapper
                ));
    }

    public Map<Client, PersonInfo> getPersonInfoforClients(List<Client> clients) {

        Map<Client, PersonInfo> personInfoForClients = new HashMap<>();
        ;
        PersonInfo pi;
        for (Client client : clients) {
            pi = persons.stream().filter(c -> c.getClientId().equals(client.getId())).findFirst().orElse(null);
            personInfoForClients.put(client, pi);

        }
        return personInfoForClients;
    }
}
