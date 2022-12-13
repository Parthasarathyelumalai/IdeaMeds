/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * <p>
 * Represents the Warehouse Response DTO
 * </p>
 *
 * @author Dinesh Kumar R
 * @version 1.0
 * @since - 2022-11-21
 */
@Getter
@Setter
@NoArgsConstructor
public class WarehouseResponseDTO {
    private Long warehouseId;

    private String warehouseName;

    private String location;

    private List<BrandItemDTO> brandItemsDTOs;
}
