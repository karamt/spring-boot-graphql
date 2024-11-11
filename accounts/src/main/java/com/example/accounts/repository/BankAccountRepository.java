package com.example.accounts.repository;

import com.example.accounts.entity.BankAccountJPA;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccountJPA, Long> {
}
