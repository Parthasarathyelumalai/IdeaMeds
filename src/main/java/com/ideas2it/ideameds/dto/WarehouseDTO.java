package com.ideas2it.ideameds.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


/**
 * <p>
 * Represents the Warehouse DTO
 * </p>
 *
 * @author Dinesh Kumar R
 * @version 1.0
 * @since - 2022-11-21
 */
@Data
@NoArgsConstructor
public class WarehouseDTO {
    private Long warehouseId;

    @NotBlank(message = "warehouse name should be mentioned")
    private String warehouseName;

    @NotBlank(message = "brand location should be mentioned")
    private String location;
}
