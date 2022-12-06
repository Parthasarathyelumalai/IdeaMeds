/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.model;

import com.ideas2it.ideameds.util.MedicineType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Simple JavaBean domain object representing Prescription Items
 * @author - Nithish K
 * @version - 1.0
 * @since - 2022-11-17
 */
@Entity
@Setter
@Getter
public class PrescriptionItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prescriptionItemsId;

    @NotNull
    private int quantity;

    @NotNull
    private int dosage;

    @NotNull
    @Enumerated(EnumType.STRING)
    private MedicineType medicineType;

    @NotNull
    private String brandItemName;
}