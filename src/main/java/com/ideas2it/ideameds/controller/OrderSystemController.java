/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.dto.OrderSystemDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.service.OrderSystemService;
import com.ideas2it.ideameds.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<OrderSystemDTO> addOrder(@PathVariable("id") Long userId) throws CustomException {
        Optional<OrderSystemDTO> orderSystem = orderSystemService.addOrder(userId);
        return orderSystem.map(orderSystemDTO -> ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderSystemDTO)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    /**
     * All users order details.
     * @return - All users order details.
     */
    @GetMapping("/allorder")
    public ResponseEntity<List<OrderSystemDTO>> getAllOrder() throws CustomException {
        List<OrderSystemDTO> orderSystemDTOList = orderSystemService.getAllOrder();
        if (null != orderSystemDTOList)
            return (ResponseEntity.status(HttpStatus.ACCEPTED).body(orderSystemDTOList));
        else
            throw new CustomException(Constants.ORDER_ITEM_NOT_FOUND);
    }

    /**
     * To get one order details by user id.
     * @param userId - To get one user order.
     * @return - One user order details
     */
    @GetMapping("/order/{id}")
    public ResponseEntity<List<OrderSystemDTO>> getOrderByUserId(@PathVariable("id") Long userId) throws CustomException {
        Optional<List<OrderSystemDTO>> orderSystem = orderSystemService.getOrderByUserId(userId);
        if (orderSystem.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(orderSystem.get());
        } else {
            throw new CustomException(Constants.ORDER_ITEM_NOT_FOUND);
        }
    }

    /**
     * Get all previous order items for given user id.
     * @param userId - To get previous order items.
     * @return All previous order items.
     */
    @GetMapping("/previousorder/{id}")
    public ResponseEntity<List<OrderSystemDTO>> getUserPreviousOrder(@PathVariable("id") Long userId) throws CustomException {
        Optional<List<OrderSystemDTO>> orderSystemList = orderSystemService.getUserPreviousOrder(userId);
        if (orderSystemList.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(orderSystemList.get());
        } throw new CustomException(Constants.NO_HISTORY_OF_ORDERS);
    }

    @DeleteMapping("/order/{id}")
    public ResponseEntity<String> cancelOrder(@PathVariable("id") Long userId) throws CustomException {
        boolean isCancel = orderSystemService.cancelOrder(userId);
        if (isCancel) {
            return ResponseEntity
                    .status(HttpStatus.GONE)
                    .body(Constants.ORDER_CANCELED_SUCCESSFULLY);
        } throw new CustomException(Constants.CAN_NOT_CANCEL_THE_ORDER);
    }
}
