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
     * It creates an order for a user.
     *
     * @param userId The id of the user who wants to order.
     * @return ResponseEntity<OrderDTO>
     * @throws CustomException - Can not place order.
     */
    @PutMapping("/order/{id}")
    public ResponseEntity<OrderDTO> addOrderByUserId(@PathVariable("id") Long userId) throws CustomException {
        Optional<OrderDTO> orderSystem = orderService.addOrder(userId);
        if (orderSystem.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(orderSystem.get());
        } else {
            throw new CustomException(HttpStatus.NO_CONTENT, Constants.CAN_NOT_ORDER);
        }
    }

    /**
     * This function is used to get all the orders details from the database.
     *
     * @return ResponseEntity<List<OrderDTO>>
     * @throws CustomException -Can not get all order.
     */
    @GetMapping("/allOrder")
    public ResponseEntity<List<OrderDTO>> getAllOrder() throws CustomException {
        List<OrderDTO> orderDTOList = orderService.getAllOrder();
        if (null != orderDTOList) {
            return (ResponseEntity.status(HttpStatus.ACCEPTED).body(orderDTOList));
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, Constants.ORDER_ITEM_NOT_FOUND);
        }
    }

    /**
     * It returns a list of orders for a given user id.
     *
     * @param userId The userId of the user whose order you want to see.
     * @return A list of OrderDTO objects.
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
            throw new CustomException(HttpStatus.NOT_FOUND, Constants.ORDER_ITEM_NOT_FOUND);
        }
    }

    /**
     * It cancels the order of the user with the given userId and orderId
     *
     * @param userId The userId of the user who wants to cancel the order.
     * @param orderId The order ID of the order to be canceled.
     * @return ResponseEntity<String>
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
            throw new CustomException(HttpStatus.NOT_FOUND, Constants.CAN_NOT_CANCEL_THE_ORDER);
        }
    }
}
