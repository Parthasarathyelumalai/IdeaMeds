/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.util;

/**
 * Enum Illness Categories
 *
 * @author - Parthasarathy Elumalai
 * @version - 1.0
 * @since - 2022-11-18
 */
public enum IllnessCategories {

    STOMACHPAIN(0),
    FEVER(1),
    BODYPAIN(2),
    LOWIMMUNITY(3);


    public final int value;

    /**
     * Constructs a new object
     *
     * @param value - pass a value of enum
     */
    IllnessCategories(final int value) {
        this.value = value;
    }

    public static IllnessCategories getIllnessCategories(int value) {
        IllnessCategories result = null;
        IllnessCategories[] type = IllnessCategories.values();
        for (IllnessCategories illnessCategorie : type) {
            if ( illnessCategorie.value == value ) {
                return illnessCategorie;
            }
        }
        return result;
    }
}
