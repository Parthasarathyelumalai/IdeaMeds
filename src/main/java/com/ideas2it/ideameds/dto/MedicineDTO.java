/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.dto;

import com.ideas2it.ideameds.util.Constants;
import com.ideas2it.ideameds.util.IllnessCategories;
import com.ideas2it.ideameds.util.MedicineType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
@Getter
@Setter
@NoArgsConstructor
public class MedicineDTO {
    private Long medicineId;

    @NotBlank(message = "name should be mentioned")
    @Pattern(regexp = Constants.REGEX_FOR_MEDICINE_NAME, message = "Invalid format - Enter a valid Medicine Name")
    private String medicineName;

    @NotBlank(message = "description should be mentioned")
    @Pattern(regexp = Constants.REGEX_FOR_PARAGRAPHS, message = "Invalid format - Enter a valid Description")
    private String description;

    @NotNull(message = "Illness category should be mentioned")
    private IllnessCategories illnessCategories;

    @NotNull(message = "medicine type should be mentioned")
    private MedicineType medicineType;

    private boolean isPrescriptionRequired;
}
