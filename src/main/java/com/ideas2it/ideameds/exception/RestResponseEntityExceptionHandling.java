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
     * It takes a CustomException object as an argument, creates an ErrorMessageDTO object with the CustomException's HTTP
     * status and message, logs the error message, and returns a ResponseEntity with the HTTP status and ErrorMessageDTO
     * object
     *
     * @param customException The exception object that is thrown by the application.
     * @return ResponseEntity<ErrorMessageDTO> - gives a response as error msg and http status code
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorMessageDTO> commonException(CustomException customException) {
        ErrorMessageDTO errorMessage = new ErrorMessageDTO(customException.getHttpStatus(), customException.getMessage());
        log.error(customException.getMessage());
        return ResponseEntity.status(customException.getHttpStatus()).body(errorMessage);
    }

    /**
     * It takes a MethodArgumentNotValidException as an argument, and returns a ResponseEntity with a map of field names
     * and error messages
     *
     * @param methodArgumentNotValidException This is the exception that is thrown when the validation fails.
     * @return A map of field names and error messages  - gives a response as error msg and http status code.
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
