/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.dto;

import com.ideas2it.ideameds.util.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * <p>
 * Represents the Brand DTO
 * </p>
 *
 * @author Dinesh Kumar R
 * @version 1.0
 * @since - 2022-11-18
 */
@Getter
@Setter
@NoArgsConstructor
public class BrandDTO {
    private Long brandId;

    @NotBlank(message = "brand name should be mentioned")
    @Pattern(regexp = Constants.REGEX_FOR_MEDICINE_NAME, message = "Invalid format - Enter a valid brand name")
    private String brandName;

    @NotBlank(message = "brand location should be mentioned")
    @Pattern(regexp = Constants.REGEX_FOR_TEXT, message = "Invalid format - Enter a valid Brand location")
    private String location;

    @NotBlank(message = "Brand description should be mentioned")
    @Pattern(regexp = Constants.REGEX_FOR_PARAGRAPHS, message = "Invalid format - Enter a valid description about brand")
    private String description;
}
