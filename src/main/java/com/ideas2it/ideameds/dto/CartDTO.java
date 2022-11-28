/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.dto;

import com.ideas2it.ideameds.model.Discount;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CartDTO {

    private Long cartId;
    private float totalPrice;
    private float discountPrice;
    private float discountPercentage;
    private Discount discount;
    private String userName;
    private List<CartItemDto> cartItemDtoList;
}
