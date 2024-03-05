package com.nttdata.account.model.enums;

import lombok.Getter;

@Getter
public enum CreditTypeEnum {
    LOAN("LOAN"),
    CREDIT_CARD("CREDIT_CARD");
    private String value;

    CreditTypeEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static CreditTypeEnum fromValue(String value) {
        for (CreditTypeEnum b : CreditTypeEnum.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}
