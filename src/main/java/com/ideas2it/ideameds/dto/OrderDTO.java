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
 * Represents the Order System Dto.
 * @author Soundharrajan S.
 * @version 1.0
 * @since - 2022-11-30
 */
@Getter
@Setter
@NoArgsConstructor
public class OrderDTO {
    private Long orderId;
    private String orderDate;
    private String deliveryDate;
    private float totalPrice;
    private float discountPrice;
    private float discountPercentage;
    private List<OrderItemDTO> orderItemDTOList;
}
