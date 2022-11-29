package com.ideas2it.ideameds.dto;

import com.ideas2it.ideameds.util.Constants;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


/**
 * <p>
 * Represents the Warehouse DTO
 * </p>
 *
 * @author Dinesh Kumar R
 * @version 1.0
 * @since - 2022-11-21
 */
@Getter
@Setter
@NoArgsConstructor
public class WarehouseDTO {
    private Long warehouseId;

    @NotBlank(message = "warehouse name should be mentioned")
    @Pattern(regexp = Constants.REGEX_FOR_TEXT, message = "Invalid format - Enter a valid Warehouse name")
    private String warehouseName;

    @NotBlank(message = "brand location should be mentioned")
    @Pattern(regexp = Constants.REGEX_FOR_TEXT, message = "Invalid format - Enter a valid Warehouse location")
    private String location;
}
