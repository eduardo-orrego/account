package com.nttdata.account.model.enums;

import lombok.Getter;

@Getter
public enum AccountTypeEnum {
    SAVINGS("SAVINGS"),
    CHECKING("CHECKING"),
    TERM_DEPOSIT("TERM_DEPOSIT");

    private final String value;

    AccountTypeEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static AccountTypeEnum fromValue(String value) {
        for (AccountTypeEnum b : AccountTypeEnum.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}
