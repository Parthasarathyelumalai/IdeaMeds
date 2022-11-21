package com.ideas2it.ideameds.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents the Prescription Items DTO
 * @author Nithish K
 * @version 1.0
 * @since - 2022-11-17
 */
@Getter
@Setter
@NoArgsConstructor
public class PrescriptionItemsDTO {
    private Long prescriptionItemsId;
    private int quantity;
    private int dosage;
    private String medicine_type;
}
