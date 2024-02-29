package com.nttdata.account.model.customer;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private String id;
    private String typeCustomer;
    private String status;
    private Address address;
    private List<IdentificationDocument> identificationDocuments;
    private PersonalInfo personalInfo;
    private BusinessInfo businessInfo;
    private LocalDateTime dateCreated;
    private LocalDateTime lastUpdated;
}

