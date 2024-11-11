package com.example.accounts.entity;

import com.example.accounts.domain.Currency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="BankAccount")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long accountId;

    @Column
    private Long clientId;

    @Column
    private Currency currency;

    @Column
    private Float balance;

    @Column
    private String status;
}
