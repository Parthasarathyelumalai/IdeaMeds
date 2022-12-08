package com.ideas2it.ideameds.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Custom exception occurs in all scenario
 *
 * @author Parthasarathy Elumalai
 * @version 1.0
 * @since 2022-11-21
 */
@Getter
public class CustomException extends Exception {

    private final HttpStatus httpStatus;

    /**
     * This is a constructor of CustomException class.
     * @param httpStatus - pass http status
     * @param message - pass error message
     */
    public CustomException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
