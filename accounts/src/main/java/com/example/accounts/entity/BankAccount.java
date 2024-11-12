package com.example.accounts.entity;

import com.example.accounts.domain.Currency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;


@Entity
@Table(name="BankAccount")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BankAccount {
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_sequence")
    @SequenceGenerator(name = "my_sequence", sequenceName = "my_sequence", initialValue = 100001)
    @Column(nullable=false, updatable=false)
    private Long accountId;

    @Column
    private Long clientId;

    @Column
    private Currency currency;

    @Column
    private String country;

    @Column
    private Float balance;

    @Column
    private String status;

    @Column
    private Float transferLimit;

    @Column
    private LocalDateTime accountCreationDate;
}
