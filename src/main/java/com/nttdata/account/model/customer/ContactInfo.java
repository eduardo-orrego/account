package com.nttdata.account.model.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactInfo {
    private String legalRepresentativeName;
    private String email;
    private String phoneNumber;
}
