/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.util;

public enum Role {
    ADMIN(0),
    CUSTOMER(1);

    public final int value;

    Role(final int value) {
        this.value = value;
    }

    public static Role getRoleType(int value) {
        Role result = null;
        Role type[] = Role.values();
        for (Role role : type) {
            if ( role.value == value ) {
                return role;
            }
        }
        return result;
    }
}
