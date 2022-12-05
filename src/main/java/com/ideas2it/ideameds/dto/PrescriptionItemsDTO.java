/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Represents the Prescription Items DTO
 *
 * @author Nithish K
 * @version 1.0
 * @since - 2022-11-17
 */
@Getter
@Setter
@ToString
public class PrescriptionItemsDTO {
    private Long prescriptionItemsId;

    @NotNull(message = "Quantity cannot be empty")
    private int quantity;

    @NotNull(message = "Dosage cannot be empty")
    private int dosage;

    @NotBlank(message = "Medicine Type cannot be empty")
    private String medicineType;

    @NotBlank(message = "Medicine Name cannot be empty")
    private String brandItemName;
}
