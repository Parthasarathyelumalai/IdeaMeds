/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.util;

public enum MedicineType {

    TABLET(0),
    SYRUP(1);

    public final int value;

    MedicineType(final int value) {
        this.value = value;
    }

    public static MedicineType getMedicineType(int value) {
        MedicineType result = null;
        MedicineType type[] = MedicineType.values();
        for (MedicineType medicineType : type) {
            if ( medicineType.value == value ) {
                return medicineType;
            }
        }
        return result;
    }
}
