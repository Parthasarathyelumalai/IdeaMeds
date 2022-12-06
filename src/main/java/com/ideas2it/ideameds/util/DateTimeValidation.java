/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.util;

import com.ideas2it.ideameds.exception.CustomException;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 * Validates the Date and time
 *
 * @author Nithish K
 * @version 1.0
 * @since 2022-11-22
 */
public class DateTimeValidation {

    /**
     * Validates the prescription date of issue
     *
     * @param issuedDate Get the date for validation
     * @throws CustomException if prescription's date is exceeded by 6 month
     *                         it will throw custom exception
     */
    public static void validateDateOfIssue(LocalDate issuedDate) throws CustomException {
        final int MAXIMUM_MONTH = 6;
        LocalDate currentDate = LocalDate.now();
        Period monthDifference = Period.between(currentDate, issuedDate);
        if (monthDifference.getMonths() >= MAXIMUM_MONTH) throw new CustomException(HttpStatus.NOT_ACCEPTABLE, Constants.PRESCRIPTION_EXPIRED);
    }

    /**
     * Get Created and modified date
     *
     * @return datetime - gives date and time;
     */
    public static LocalDateTime getDate() {
        String currentDateAndTime;
        String dateTimeFormatter = "yyyy-MM-dd HH:mm:ss";
        LocalDateTime currentDate = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(dateTimeFormatter);
        currentDateAndTime = currentDate.format(dateFormatter);
        return LocalDateTime.parse(currentDateAndTime, dateFormatter);
    }
}
