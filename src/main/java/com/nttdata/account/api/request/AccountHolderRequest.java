package com.nttdata.account.api.request;

import com.nttdata.account.enums.HolderTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountHolderRequest {

    private String holderId;

    private HolderTypeEnum holderType;

}
