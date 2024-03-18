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
public class BusinessInfo {

    private String subType;
    private String legalName;
    private String tradeName;
    private LocalDate incorporationDate;
    private String website;
    private String fax;

}
