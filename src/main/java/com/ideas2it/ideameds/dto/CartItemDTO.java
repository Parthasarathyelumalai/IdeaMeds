/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents the Cart Item Dto.
 * @author Soundharrajan S.
 * @version 1.0
 * @since - 2022-11-30
 */
@Getter
@Setter
@NoArgsConstructor
public class CartItemDTO {
    private Long cartItemId;
    private int quantity;
    private MedicineDTO medicineDTO;
    private BrandItemsDTO brandItemsDTO;
}