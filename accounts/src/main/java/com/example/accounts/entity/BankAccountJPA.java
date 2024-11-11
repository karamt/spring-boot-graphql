package com.example.accounts.entity;

import com.example.accounts.domain.Currency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="BankAccount")
public class BankAccountJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private long clientId;

    @Column
    private Currency currency;

    @Column
    private Float balance;

    @Column
    private String status;
}
