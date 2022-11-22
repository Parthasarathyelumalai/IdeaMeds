package com.ideas2it.ideameds.exception;

/**
 * Custom exception occurs when Prescription is expired
 * @author Nithish K
 * @version 1.0
 * @since 2022-11-21
 */
public class PrescriptionExpiredException extends Exception {
    public PrescriptionExpiredException(String message) {
        super(message);
    }
}
