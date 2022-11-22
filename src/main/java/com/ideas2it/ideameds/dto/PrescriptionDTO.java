package com.ideas2it.ideameds.dto;

import com.ideas2it.ideameds.util.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

/**
 * Represents the Prescription DTO
 * @author Nithish K
 * @version 1.0
 * @since - 2022-11-17
 */
@Getter
@Setter
@NoArgsConstructor
public class PrescriptionDTO {
    private Long prescriptionId;

    @NotBlank(message = "Doctor name should be mentioned")
    @Pattern(regexp = "^[a-zA-z]{1}[a-zA-Z\\s]*$", message = "Invalid format - e.g. Firstname LastName")
    private String doctorName;

    @NotBlank(message = "Clinic Address should be mentioned")
    @Pattern(regexp = "^[a-zA-z]{1}[a-zA-Z\\s]*$", message = "Invalid format")
    private String clinicAddress;

    @NotBlank(message = "patient Name should be mentioned")
    @Pattern(regexp = "^[a-zA-z]{1}[a-zA-Z\\s]*$", message = "Invalid format - e.g. Firstname LastName")
    private String patientName;

    @NotBlank(message = "Gender should be mentioned")
    @Enumerated(EnumType.STRING)
    private Gender patientGender;

    @NotBlank(message = "Age is required")
    @Pattern(regexp = "^[0-9.]{1,3}", message = "Invalid format - Should contain only numbers")
    @Min(value = 1, message = "Age should not be less than 1")
    @Max(value = 130, message = "Age should not be greater than 130")
    private int patientAge;

    @NotBlank(message = "Prescription issued date is required")
    @PastOrPresent(message = "Prescription date should not be in future")
    private String dateOfIssue;
}