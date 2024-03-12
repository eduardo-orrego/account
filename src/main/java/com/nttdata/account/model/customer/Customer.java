package com.nttdata.account.model.customer;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    private String id;
    private String type;
    private String status;
    private Address address;
    private IdentificationDocument identificationDocument;
    private PersonalInfo personalInfo;
    private BusinessInfo businessInfo;
    private LocalDateTime dateCreated;
    private LocalDateTime lastUpdated;

}

