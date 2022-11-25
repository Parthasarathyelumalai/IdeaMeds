/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.dto.OrderSystemDTO;
import com.ideas2it.ideameds.model.OrderSystem;
import com.ideas2it.ideameds.service.OrderSystemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Controller for place an order.
 *
 * @author - Soundharrajan.S
 * @version - 1.0
 * @since - 2022-11-21
 */

@RestController
@RequiredArgsConstructor
public class OrderSystemController {

    private final OrderSystemService orderSystemService;

    /**
     * save order details in repository.
     * @param userId - To get user and cart details. Then map with order.
     * @return - Price of the order (total price, discount price, discount percentage).
     */
    @PutMapping("/order/{id}")
    public ResponseEntity<String> addOrder(@PathVariable("id") Long userId) {
        Optional<OrderSystemDTO> orderSystem = orderSystemService.addOrder(userId);
        if (orderSystem.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Order successful."
                            + "\nTotal price : " + orderSystem.get().getTotalPrice()
                            + "\nDiscount : " + orderSystem.get().getDiscountPercentage() + "%"
                            + "\nDiscount price : " + orderSystem.get().getDiscountPrice());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("order unsuccessful");
    }

    /**
     * All users order details.
     * @return - All users order details.
     */
    @GetMapping("/allorder")
    public ResponseEntity<List<OrderSystem>> getAllOrder() {
        return (ResponseEntity.status(HttpStatus.ACCEPTED).body(orderSystemService.getAllOrder()));
    }

    /**
     * To get one order details by user id.
     * @param userId - To get one user order.
     * @return - One user order details
     */
    @GetMapping("/order/{id}")
    public ResponseEntity<OrderSystem> getOrderByUserId(@PathVariable("id") Long userId) {
        Optional<OrderSystem> orderSystem = orderSystemService.getOrderByUserId(userId);
        if (orderSystem.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(orderSystem.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Get all previous order items for given user id.
     * @param userId - To get previous order items.
     * @return All previous order items.
     */
    @GetMapping("/previousorder/{id}")
    public ResponseEntity<List<OrderSystem>> getUserPreviousOrder(@PathVariable("id") Long userId) {
        List<OrderSystem> orderSystemList = orderSystemService.getUserPreviousOrder(userId);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(orderSystemList);
    }
}
