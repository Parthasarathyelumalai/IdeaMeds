/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.dto.OrderDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.service.OrderService;
import com.ideas2it.ideameds.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
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
public class OrderController {
    private final OrderService orderService;

    /**
     * Creates object for the class
     *
     * @param orderService to create order system service object
     */
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * save order details in repository.
     *
     * @param userId - To get user and cart details. Then map with order.
     * @return - Price of the order (total price, discount price, discount percentage).
     * @throws CustomException - Can not order items.
     */
    @PutMapping("/order/{id}")
    public ResponseEntity<OrderDTO> addOrderByUserId(@PathVariable("id") Long userId) throws CustomException {
        Optional<OrderDTO> orderSystem = orderService.addOrder(userId);
        if (orderSystem.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(orderSystem.get());
        } else {
            throw new CustomException(Constants.CAN_NOT_ORDER);
        }
    }

    /**
     * All users order details.
     *
     * @return - All users order details.
     * @throws CustomException -Can not get all order.
     */
    @GetMapping("/allOrder")
    public ResponseEntity<List<OrderDTO>> getAllOrder() throws CustomException {
        List<OrderDTO> orderDTOList = orderService.getAllOrder();
        if (null != orderDTOList) {
            return (ResponseEntity.status(HttpStatus.ACCEPTED).body(orderDTOList));
        } else {
            throw new CustomException(Constants.ORDER_ITEM_NOT_FOUND);
        }
    }

    /**
     * To get one order details by user id.
     *
     * @param userId - To get one user order.
     * @return - One user order details.
     * @throws CustomException - Order item not found.
     */
    @GetMapping("/order/{id}")
    public ResponseEntity<List<OrderDTO>> getOrderByUserId(@PathVariable("id") Long userId) throws CustomException {
        Optional<List<OrderDTO>> orderSystem = orderService.getOrderByUserId(userId);
        if (orderSystem.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(orderSystem.get());
        } else {
            throw new CustomException(Constants.ORDER_ITEM_NOT_FOUND);
        }
    }

    /**
     * Cancel one order by user and order id.
     * @param userId - To get user from repository.
     * @param orderId - To delete order by order id.
     * @return Deleted message.
     * @throws CustomException - Can not cancel the order.
     */
    @DeleteMapping("/order/{userId}/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable("userId") Long userId, @PathVariable("orderId") Long orderId) throws CustomException {
        boolean isCancel = orderService.cancelOrder(userId, orderId);
        
        if (isCancel) {
            return ResponseEntity
                    .status(HttpStatus.GONE)
                    .body(Constants.ORDER_CANCELED_SUCCESSFULLY);
        } else {
            throw new CustomException(Constants.CAN_NOT_CANCEL_THE_ORDER);
        }
    }
}
