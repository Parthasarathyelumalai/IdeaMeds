package com.ideas2it.ideameds.exception;

import com.ideas2it.ideameds.dto.ErrorMessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>RestResponse Entity Exception Handling
 * </p>
 *
 * @author Parthasarathy elumalai
 * @version 1.0
 * @since 2022-11-21
 */
@ControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandling {

    /**
     * method used to handle Custom exception
     *
     * @param customException - send exception
     * @return errorMessage - gives a response as error msg and http status code
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorMessageDTO> commonException(CustomException customException) {
        ErrorMessageDTO errorMessage = new ErrorMessageDTO(customException.getHttpStatus(), customException.getMessage());
        log.error(customException.getMessage());
        return ResponseEntity.status(customException.getHttpStatus()).body(errorMessage);
    }

    /**
     * method used to handle MethodArgumentNotValid exception
     *
     * @param methodArgumentNotValidException - send exception
     * @return errorMessage - gives a response as error msg and http status code
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException methodArgumentNotValidException) {
        Map<String, String> errors = new HashMap<>();

        methodArgumentNotValidException.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.NOT_ACCEPTABLE);
    }
}
