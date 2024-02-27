package com.nttdata.account.model.enums;

import lombok.Getter;

@Getter
public enum MovementLimitTypeEnum {
    SPECIFIC_DAY_MONTH("specific_day_month"),
    MONTHLY("monthly"),
    NO_LIMIT("no_limit");

    private final String value;

    MovementLimitTypeEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static MovementLimitTypeEnum fromValue(String value) {
        for (MovementLimitTypeEnum b : MovementLimitTypeEnum.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}
