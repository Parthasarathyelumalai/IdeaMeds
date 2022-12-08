/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.util;

/**
 * Enum Gender
 *
 * @author - Parthasarathy Elumalai
 * @version - 1.0
 * @since - 2022-11-18
 */
public enum Gender {
    MALE(0),
    FEMALE(1),
    OTHER(2);

    public final int value;

    /**
     * Constructs a new object
     *
     * @param value - pass a value of enum
     */
    Gender(final int value) {
        this.value = value;
    }

    /**
     * It loops through all the values of the enum and returns the one that matches the value passed in
     *
     * @param value The value of the enumeration.
     * @return The Gender enum is being returned.
     */
    public static Gender getMedicineType(int value) {
        Gender result = null;
        Gender[] type = Gender.values();
        for (Gender gender : type) {
            if ( gender.value == value ) {
                return gender;
            }
        }
        return result;
    }
}
