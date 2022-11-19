package com.ideas2it.ideameds.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String doctorName;
    private String clinicAddress;
    private String patientName;
    private String patientGender;
    private int patientAge;
    private String dateOfIssue;
}