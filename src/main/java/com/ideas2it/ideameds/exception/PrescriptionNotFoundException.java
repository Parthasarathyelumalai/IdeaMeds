/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.exception;

/**
 * Custom exception occurs when Prescription is not found
 * in the database
 * @author Nithish K
 * @version 1.0
 * @since 2022-11-21
 */
public class PrescriptionNotFoundException extends Exception {
    public PrescriptionNotFoundException(String message) {
        super(message);
    }
}
