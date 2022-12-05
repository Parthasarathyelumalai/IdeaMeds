/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.dto;

import com.ideas2it.ideameds.util.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Simple JavaBean domain object representing a User Medicine.
 *
 * @author - Parthasarathy Elumalai
 * @version - 1.0
 * @since - 2022-11-17
 */
@Getter
@Setter
@NoArgsConstructor
public class UserMedicineDTO {
    private Long userMedicineId;
    @NotBlank(message = "Medicine name should be mentioned")
    @Pattern(regexp = Constants.REGEX_FOR_BRAND_ITEM_NAME, message = "Invalid format")
    private String medicineName;
    @NotNull
    @Min(1)
    private int quantity;
}
