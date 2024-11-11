package com.example.accounts.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Person {
    private Long personId;
    private Long clientId;
    private String firstName;
    private String middleName;
    private String lastName;
}
