/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.model;

import com.ideas2it.ideameds.util.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Simple JavaBean domain object representing Prescription
 * @author - Nithish K
 * @version - 1.0
 * @since - 2022-11-17
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prescriptionId;

    @NotNull
    private String doctorName;

    @NotNull
    private String clinicAddress;

    @NotNull
    private String patientName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender patientGender;

    @NotNull
    private int patientAge;

    @NotNull
    private LocalDate dateOfIssue;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    @Column(columnDefinition = "BIT default 0")
    @NotNull
    private boolean deletedStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "prescription_id")
    @NotNull
    private List<PrescriptionItems> prescriptionItems;
}