/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.dto;

import com.ideas2it.ideameds.model.Cart;
import com.ideas2it.ideameds.model.OrderItem;
import com.ideas2it.ideameds.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderSystemDTO {
    private Long orderId;
    private String orderDate;
    private String deliveryDate;
    private String orderStatus;
    private float totalPrice;
    private float discountPrice;
    private float discountPercentage;
    private int deletedStatus;
    private User user;
    private Cart cart;
    private List<OrderItem> orderItemList;
}
