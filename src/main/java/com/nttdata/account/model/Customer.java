package com.nttdata.account.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    private String id;

    @JsonProperty(value = "type_customer")
    private String typeCustomer;

    @JsonProperty(value = "full_name")
    private String fullName;

    @JsonProperty(value = "legal_name")
    private String legalName;

    @JsonProperty(value = "dni")
    private String dni;

    @JsonProperty(value = "ruc")
    private String ruc;

    @JsonProperty(value = "address")
    private String address;

    @JsonProperty(value = "phone_number")
    private String phoneNumber;

}

