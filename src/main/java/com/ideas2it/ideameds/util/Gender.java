/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.util;

/**
 * Enum Gender
 *
 * @author - Parthasarathy Elumalai
 * @since - 2022-11-18
 * @version - 1.0
 */
public enum Gender {
    MALE(0),
    FEMALE(1),
    OTHER(2);

    public final int value;

    Gender(final int value) {
        this.value = value;
    }

    public static Gender getMedicineType(int value) {
        Gender result = null;
        Gender type[] = Gender.values();
        for (Gender gender : type) {
            if ( gender.value == value ) {
                return gender;
            }
        }
        return result;
    }
}
