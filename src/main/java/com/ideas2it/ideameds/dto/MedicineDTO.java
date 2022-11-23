package com.ideas2it.ideameds.dto;

import com.ideas2it.ideameds.util.IllnessCategories;
import com.ideas2it.ideameds.util.MedicineType;
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
    private IllnessCategories illnessCategories;
    private MedicineType medicineType;
    private boolean prescriptionRequired;
}
