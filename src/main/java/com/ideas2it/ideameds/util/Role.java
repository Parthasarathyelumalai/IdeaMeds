/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.util;

/**
 * Enum Role Type
 *
 * @author - Parthasarathy Elumalai
 * @version - 1.0
 * @since - 2022-11-18
 */
public enum Role {
    ROLE_ADMIN(0),
    ROLE_CUSTOMER(1);

    public final int value;

    /**
     * Constructs a new object
     * @param value - pass a value of enum
     */
    Role(final int value) {
        this.value = value;
    }

    /**
     * get role type
     *
     * @param value - pass a value of enum
     * @return role - value of role
     */
    public static Role getRoleType(int value) {
        Role result = null;
        Role[] type = Role.values();
        for (Role role : type) {
            if ( role.value == value ) {
                return role;
            }
        }
        return result;
    }
}
