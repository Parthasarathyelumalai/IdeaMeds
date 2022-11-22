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
    public ResponseEntity<ErrorMessageDTO> commonException(UserException userException, WebRequest webRequest) {
        ErrorMessageDTO errorMessage = new ErrorMessageDTO(HttpStatus.NOT_FOUND, userException.getMessage());
        log.error(userException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(MedicineNotFoundException.class)
    public ResponseEntity<ErrorMessageDTO> medicineNotFoundException(MedicineNotFoundException medicineNotFoundException, WebRequest webRequest) {
        ErrorMessageDTO errorMessage = new ErrorMessageDTO(HttpStatus.NOT_FOUND, medicineNotFoundException.getMessage());
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

    @ExceptionHandler(PrescriptionNotFoundException.class)
    public ResponseEntity<ErrorMessageDTO> prescriptionNotFoundException(PrescriptionNotFoundException prescriptionNotFoundException) {
        ErrorMessageDTO errorMessage = new ErrorMessageDTO(HttpStatus.NOT_FOUND, prescriptionNotFoundException.getMessage());
        log.error(prescriptionNotFoundException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }
}
