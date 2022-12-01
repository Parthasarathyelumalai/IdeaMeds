package com.ideas2it.ideameds.util;

public enum OrderStatus {
    ORDER_STATUS_DELIVERED(0),
    ORDER_STATUS_SUCCESSFUL(1);

    public final int value;

    OrderStatus(final int value) {
        this.value = value;
    }
}
