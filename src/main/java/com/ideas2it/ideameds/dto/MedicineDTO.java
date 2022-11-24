package com.ideas2it.ideameds.dto;

import com.ideas2it.ideameds.util.IllnessCategories;
import com.ideas2it.ideameds.util.MedicineType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


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
    @NotBlank(message = "name should be mentioned")
    private String medicineName;
    @NotBlank(message = "description should be mentioned")
    private String description;
    @NotBlank(message = "Illness category should be mentioned")
    private IllnessCategories illnessCategories;
    @NotBlank(message = "medicine type should be mentioned")
    private MedicineType medicineType;
    @NotBlank(message = "prescription required should be mentioned")
    private boolean prescriptionRequired;
}
