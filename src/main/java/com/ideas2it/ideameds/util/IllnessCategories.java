package com.ideas2it.ideameds.util;

public enum IllnessCategories {

    STOMACHPAIN(1),
    HEADACHE(2),
    BODYPAIN(3);


    public final int value;

    IllnessCategories(final int value) {
        this.value = value;
    }

    public static IllnessCategories getIllnessCategories(int value) {
        IllnessCategories result = null;
        IllnessCategories type[] = IllnessCategories.values();
        for (IllnessCategories illnessCategorie : type) {
            if ( illnessCategorie.value == value ) {
                return illnessCategorie;
            }
        }
        return result;
    }
}
