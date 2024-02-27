package com.nttdata.account.model.enums;

import lombok.Getter;

@Getter
public enum CurrencyTypeEnum {
    PEN("PEN"),
    USD("USD");

    private String value;

    CurrencyTypeEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static CurrencyTypeEnum fromValue(String value) {
        for (CurrencyTypeEnum b : CurrencyTypeEnum.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}
