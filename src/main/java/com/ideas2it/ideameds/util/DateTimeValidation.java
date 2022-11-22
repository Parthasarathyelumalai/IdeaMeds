package com.ideas2it.ideameds.util;

import com.ideas2it.ideameds.exception.PrescriptionExpiredException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

/**
 * Validates the Date and time
 * @author Nithish K
 * @version 1.0
 * @since  2022-11-22
 */
@Component
public class DateTimeValidation {

    /**
     * Validates the prescription date of issue
     * @param issuedDate Get the date for validation
     * @throws PrescriptionExpiredException if prescription's date is exceeded by 6 month
     * it will throw custom exception
     */
    public void validateDateOfIssue(LocalDate issuedDate) throws PrescriptionExpiredException {
        final int MAXIMUM_MONTH = 6;
        LocalDate currentDate = LocalDate.now();
        Period monthDifference = Period.between(currentDate,issuedDate);
        if(monthDifference.getMonths() >= MAXIMUM_MONTH) throw new PrescriptionExpiredException("Prescription has Expired");
    }
}
