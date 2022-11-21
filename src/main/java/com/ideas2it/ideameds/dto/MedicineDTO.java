package com.ideas2it.ideameds.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

/**
 * <p>
 * Represents the Medicine DTO
 * </p>
 *
 * @author Dinesh Kumar R
 * @version 1.0
 * @since - 2022-11-19
 */
@Data
@NoArgsConstructor
public class MedicineDTO {
    private Long medicineId;
    private String medicineName;
    private String description;
    private String labelDosage;
    private String medicineUses;
    private String sideEffect;
    private float price;
    private String manufacturedDate;
    private String expiryDate;
    private String medicineImage;
}
