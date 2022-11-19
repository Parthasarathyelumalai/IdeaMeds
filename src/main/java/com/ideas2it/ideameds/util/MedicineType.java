package com.ideas2it.ideameds.util;

public enum MedicineType {

    TABLET(1),
    SYRUP(2);

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
