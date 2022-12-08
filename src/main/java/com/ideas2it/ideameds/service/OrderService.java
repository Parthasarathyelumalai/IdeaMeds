/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.OrderDTO;
import com.ideas2it.ideameds.exception.CustomException;

import java.util.List;
import java.util.Optional;

/**
 * This is a service interface for order.
 *
 * @author - Soundharrajan S.
 * @version - 1.0
 * @since - 2022-11-30
 */

public interface OrderService {

    /**
     * This function adds an order for a user and returns the order details if the order was added successfully.
     *
     * @param userId The id of the user who is placing the order.
     * @return Optional<OrderDTO>
     * @throws CustomException - user not found.
     */
    Optional<OrderDTO> addOrder(Long userId) throws CustomException;

    /**
     * This function returns a list of all orders
     *
     * @return List of OrderDTO
     * @throws CustomException - Order item not found.
     */
    List<OrderDTO> getAllOrder() throws CustomException;

    /**
     * This function returns a list of orders for a given user id
     *
     * @param userId The id of the user whose orders are to be fetched.
     * @return Optional<List<OrderDTO>>
     * @throws CustomException - User not found.
     */
    Optional<List<OrderDTO>> getOrderByUserId(Long userId) throws CustomException;

    /**
     * Cancel an order for a user by user id and order id.
     *
     * @param userId The user ID of the user who is cancelling the order.
     * @param orderId The order ID of the order to be cancelled.
     * @return A boolean value.
     */
    boolean cancelOrder(Long userId, Long orderId) throws CustomException;
}
