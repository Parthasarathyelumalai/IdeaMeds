/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.util;

/**
 * Enum MedicineType
 *
 * @author - Parthasarathy Elumalai
 * @version - 1.0
 * @since - 2022-11-18
 */
public enum MedicineType {

    TABLET(0),
    SYRUP(1),
    CAPSULE(2),
    OINTMENT(3);

    public final int value;

    /**
     * Constructs a new object
     *
     * @param value - pass a value of enum
     */
    MedicineType(final int value) {
        this.value = value;
    }

    /**
     * It loops through all the values of the enum and returns the one that matches the value passed in
     *
     * @param value The value of the enumeration.
     * @return The MedicineType enum is being returned.
     */
    public static MedicineType getMedicineType(int value) {
        MedicineType result = null;
        MedicineType[] type = MedicineType.values();
        for (MedicineType medicineType : type) {
            if ( medicineType.value == value ) {
                return medicineType;
            }
        }
        return result;
    }
}
