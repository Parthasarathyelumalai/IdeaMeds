package com.ideas2it.ideameds.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Simple JavaBean domain object representing Prescription Items
 * @author - Nithish K
 * @version - 1.0
 * @since - 2022-11-17
 */
@Data
@NoArgsConstructor
public class PrescriptionItems {
    private Long prescriptionItemsId;
    private int quantity;
    private int dosage;
    private String medicine_type;
    private Prescription prescription;
}
