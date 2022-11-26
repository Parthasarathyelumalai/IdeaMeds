/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.dto;

import com.ideas2it.ideameds.model.BrandItems;
import com.ideas2it.ideameds.model.Medicine;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderItemDTO {
    private Long orderItemId;
    private int quantity;
    private Medicine medicine;
    private BrandItems brandItems;
}
