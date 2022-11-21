package com.ideas2it.ideameds.util;

public enum Gender {
    MALE(1),
    FEMALE(2),
    OTHER(3);

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
