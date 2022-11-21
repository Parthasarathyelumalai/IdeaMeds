package com.ideas2it.ideameds.exception;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

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
@ResponseStatus
@Slf4j
public class RestResponseEntityExceptionHandling extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorMessage> commonException(UserException userException, WebRequest webRequest) {
        ErrorMessage errorMessage = new ErrorMessage(userException.getMessage());
        log.error(userException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(MedicineNotFoundException.class)
    public ResponseEntity<ErrorMessage> medicineNotFoundException(MedicineNotFoundException medicineNotFoundException, WebRequest webRequest) {
        ErrorMessage errorMessage = new ErrorMessage(medicineNotFoundException.getMessage());
        log.error(medicineNotFoundException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

/*    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException methodArgumentNotValidException) {
        Map<String, String> errors = new HashMap<>();

        methodArgumentNotValidException.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }*/
}
