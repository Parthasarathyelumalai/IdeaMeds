package com.ideas2it.ideameds.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * Represents the Brand DTO
 * </p>
 *
 * @author Dinesh Kumar R
 * @version 1.0
 * @since - 2022-11-18
 */
@Data
@NoArgsConstructor
public class BrandDTO {
    private Long brandId;

    @NotBlank(message = "brand name should be mentioned")
    private String brandName;

    @NotBlank(message = "brand location should be mentioned")
    private String location;

    @NotBlank(message = "Brand description should be mentioned")
    private String description;
}
