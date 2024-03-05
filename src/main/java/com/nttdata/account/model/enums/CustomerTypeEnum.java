package com.nttdata.account.model.enums;

import lombok.Getter;

@Getter
public enum CustomerTypeEnum {
    PERSONAL("PERSONAL"),
    BUSINESS("BUSINESS"),
    PERSONAL_VIP("PERSONAL_VIP"),
    BUSINESS_PYME("BUSINESS_PYME");;

    private String value;

    CustomerTypeEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static CustomerTypeEnum fromValue(String value) {
        for (CustomerTypeEnum b : CustomerTypeEnum.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}
