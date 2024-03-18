package com.nttdata.account.model.customer;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonalInfo {

    private String subType;
    private String fullName;
    private String nationality;
    private LocalDate birthdate;
    private String email;
    private String phoneNumber;

}
