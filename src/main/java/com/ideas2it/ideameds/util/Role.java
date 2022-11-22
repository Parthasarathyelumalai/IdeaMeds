package com.ideas2it.ideameds.util;

public enum Role {
    ADMIN(1),
    CUSTOMER(2);

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
