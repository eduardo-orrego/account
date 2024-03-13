package com.nttdata.account.enums;

import lombok.Getter;

@Getter
public enum CustomerSubTypeEnum {
    NORMAL("NORMAL"),
    VIP("VIP"),
    PYME("PYME");

    private String value;

    CustomerSubTypeEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static CustomerSubTypeEnum fromValue(String value) {
        for (CustomerSubTypeEnum b : CustomerSubTypeEnum.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}
