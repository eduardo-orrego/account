package com.nttdata.account.model.customer;

import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdentificationDocument {

    private String type;
    private BigInteger number;

}
