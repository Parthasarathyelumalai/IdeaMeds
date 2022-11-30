/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.dto;

import com.ideas2it.ideameds.util.Constants;
import com.ideas2it.ideameds.util.Gender;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Represents the Prescription DTO
 *
 * @author Nithish K
 * @version 1.0
 * @since - 2022-11-17
 */
@Getter
@Setter
public class PrescriptionDTO {
    private Long prescriptionId;

    @NotBlank(message = "Doctor name should be mentioned")
    @Pattern(regexp = Constants.REGEX_FOR_TEXT, message = "Invalid format - e.g. Firstname LastName")
    private String doctorName;

    @NotBlank(message = "Clinic Address should be mentioned")
    @Pattern(regexp = Constants.REGEX_FOR_TEXT, message = "Invalid format")
    private String clinicAddress;

    @NotBlank(message = "patient Name should be mentioned")
    @Pattern(regexp = Constants.REGEX_FOR_TEXT, message = "Invalid Format")
    private String patientName;

    @NotNull(message = "Age is required")
    @Pattern(regexp = Constants.REGEX_FOR_AGE, message = "Age should not contain letters")
    @Min(value = 1, message = "Age should not be less than 1")
    @Max(value = 130, message = "Age should not be greater than 130")
    private String patientAge;

    @NotNull(message = "Gender should be mentioned")
    private Gender patientGender;

    @NotNull(message = "Prescription issued date is required")
    @PastOrPresent(message = "Prescription date should not be in future")
    private LocalDate dateOfIssue;

    @NotNull(message = "Prescribed Medicines cannot be empty")
    private List<@Valid PrescriptionItemsDTO> prescriptionItems;
}