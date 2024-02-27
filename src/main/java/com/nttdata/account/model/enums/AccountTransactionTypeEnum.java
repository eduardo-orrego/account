package com.nttdata.account.model.enums;

import lombok.Getter;

@Getter
public enum AccountTransactionTypeEnum {
    DEPOSIT("deposit"),
    WITHDRAWAL("Withdrawal");

    private final String value;

    AccountTransactionTypeEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static AccountTransactionTypeEnum fromValue(String value) {
        for (AccountTransactionTypeEnum b : AccountTransactionTypeEnum.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}
