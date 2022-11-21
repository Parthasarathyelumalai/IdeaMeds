package com.ideas2it.ideameds.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

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
    private String doctorName;

    @NotBlank(message = "Clinic Address should be mentioned")
    private String clinicAddress;

    @NotBlank(message = "patient Name should be mentioned")
    private String patientName;

    @NotBlank(message = "Gender should be mentioned")
    private String patientGender;

    @NotBlank(message = "Age is required")
    private int patientAge;

    @NotBlank(message = "Prescription issued date is required")
    private String dateOfIssue;
}