package com.ideas2it.ideameds.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * Represents the Error Message DTO
 * @author Nithish K
 * @version 1.0
 * @since 2022-11-21
 */
@Setter
@Getter
@AllArgsConstructor
public class ErrorMessageDTO {
    private HttpStatus httpStatus;
    private String message;
}
