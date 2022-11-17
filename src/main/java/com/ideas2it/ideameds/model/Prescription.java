package com.ideas2it.ideameds.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Simple JavaBean domain object representing Prescription
 * @author - Nithish K
 * @version - 1.0
 * @since - 2022-11-17
 */
@Data
@NoArgsConstructor
public class Prescription {
    private Long prescriptionId;
    private String doctorName;
    private String clinicAddress;
    private String patientName;
    private String patientGender;
    private int patientAge;
    private String dateOfIssue;
}
