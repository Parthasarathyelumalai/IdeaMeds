package com.ideas2it.ideameds.exception;

import org.springframework.http.HttpStatus;

/**
 * Custom exception occurs in all scenario
 * @author Parthasarathy Elumalai
 * @version 1.0
 * @since 2022-11-21
 */
public class CustomException extends Exception{

    private HttpStatus httpStatus;
    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message,HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
