package com.example.accounts.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Client {
    private String id;
    private String accountId;
    private String personInfoId;
}
