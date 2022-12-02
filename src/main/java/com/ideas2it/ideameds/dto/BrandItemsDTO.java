/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.dto;

import com.ideas2it.ideameds.util.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

/**
 * <p>
 * Represents the BrandItems DTO
 * </p>
 *
 * @author Dinesh Kumar R
 * @version 1.0
 * @since - 2022-11-21
 */
@Getter
@Setter
@NoArgsConstructor
public class BrandItemsDTO {
    private Long brandItemsId;

    @NotBlank(message = "Brand Item name should be mentioned")
    @Pattern(regexp = Constants.REGEX_FOR_MEDICINE_NAME, message = "Invalid format - Enter a valid brand item name")
    private String brandItemName;

    @NotNull(message = "price should be mentioned")
    private float price;

    @NotBlank(message = "Side effect should be mentioned")
    @Pattern(regexp = Constants.REGEX_FOR_PARAGRAPHS, message = "Invalid format - Enter a valid side effect")
    private String sideEffect;

    @NotBlank(message = "Key Ingredients should be mentioned")
    @Pattern(regexp = Constants.REGEX_FOR_PARAGRAPHS, message = "Invalid format - Enter a valid key Ingredients")
    private String keyIngredients;

    @NotBlank(message = "Description should be mentioned")
    @Pattern(regexp = Constants.REGEX_FOR_PARAGRAPHS, message = "Invalid format - Enter a valid description")
    private String description;

    @NotBlank(message = "safety information should be mentioned")
    @Pattern(regexp = Constants.REGEX_FOR_PARAGRAPHS, message = "Invalid format - Enter a valid safety information")
    private String safetyInformation;

    @NotBlank(message = "Enter a Valid Dosage")
    @Pattern(regexp = Constants.REGEX_FOR_PARAGRAPHS, message = "Invalid format - Enter a valid Dosage")
    private String medicineUses;

    @NotBlank(message = "Enter a Valid Manufactured Date")
    @Pattern(regexp = Constants.REGEX_FOR_DATE, message = "Invalid format - Enter a valid manufactured rate")
    private String manufacturedDate;

    @NotBlank(message = "Enter a Valid Expiry Date")
    @Pattern(regexp = Constants.REGEX_FOR_DATE, message = "Invalid format - Enter a valid expiry rate")
    private String expiryDate;

    @NotBlank(message = "Add a image for medicine")
    private String medicineImage;

    @NotNull(message = "Enter a valid package quantity")
    @Min(1)
    @Max(9999)
    private int packageQuantity;

    private MedicineDTO medicineDTO;
}
