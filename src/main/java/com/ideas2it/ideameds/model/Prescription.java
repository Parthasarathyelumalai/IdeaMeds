package com.ideas2it.ideameds.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Simple JavaBean domain object representing Prescription
 * @author - Nithish K
 * @version - 1.0
 * @since - 2022-11-17
 */
@Entity
@Data
@NoArgsConstructor
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prescriptionId;
    private String doctorName;
    private String clinicAddress;
    private String patientName;
    private String patientGender;
    private int patientAge;
    private String dateOfIssue;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
