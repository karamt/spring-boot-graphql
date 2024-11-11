package com.example.accounts.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PersonInfo {
    private String id;
    private String clientId;
    private String firstName;
    private String middleName;
    private String lastName;
}
