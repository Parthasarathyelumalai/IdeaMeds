package com.ideas2it.ideameds.util;

import javax.xml.bind.ValidationException;

public class Validation {
    /**
     * ...validation of age...
     * @param age
     * @return boolean
     */
    public  static boolean isValidAge(int age) throws ValidationException {
        if (age > 0 && age <= 100) {
            return true;
        } else {
            throw new ValidationException("Invalid Age");
        }
    }
}
