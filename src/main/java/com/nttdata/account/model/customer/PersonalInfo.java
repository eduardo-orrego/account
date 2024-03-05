package com.nttdata.account.model.customer;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonalInfo {
    private String fullName;
    private String nationality;
    private LocalDate birthdate;
    private String email;
    private String phoneNumberPersonal;
    private String phoneNumberWork;
}
