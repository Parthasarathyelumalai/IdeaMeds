package com.ideas2it.ideameds.dto;

import com.ideas2it.ideameds.model.Cart;
import com.ideas2it.ideameds.model.Discount;
import com.ideas2it.ideameds.model.OrderItem;
import com.ideas2it.ideameds.model.User;

import java.util.List;

/**
 * Represents the Order System DTO
 * @author Parthasarathy Elumalai
 * @version 1.0
 * @since - 2022-11-19
 */
public class OrderSystemDTO {
    private Long orderId;
    private String orderDate;
    private String deliveryDate;
    private String orderStatus;
    private Discount discount;
    private double totalPrice;
    private User user;
    private Cart cart;
    private List<OrderItem> orderItemList;
}
