package com.nttdata.account.enums;

import lombok.Getter;

@Getter
public enum DebitCardStatusEnum {
    ACTIVE,
    INACTIVE,
    BLOCKED,
    CANCELLED,
    EXPIRED,
    OVERDUE
}
