/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.OrderSystemDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.model.OrderSystem;

import java.util.List;
import java.util.Optional;

/**
 * Service for placing order(order system).
 *
 * @author - Soundharrajan.S
 * @version - 1.0
 * @since - 2022-11-17
 */

public interface OrderSystemService {

    /**
     * add and save order items in repository.
     *
     * @param userId - To get user and cart details to map with order.
     * @return - Total price of order.
     */
    Optional<OrderSystemDTO> addOrder(Long userId) throws CustomException;

    /**
     * All users order details.
     * @return - All users order details.
     */
    List<OrderSystemDTO> getAllOrder() throws CustomException;

    /**
     * To get one order details by user id.
     *
     * @param userId - To get one user order.
     * @return - One user order details
     */
    Optional<OrderSystemDTO> getOrderByUserId(Long userId) throws CustomException;

    /**
     * Get all previous order items for given user id.
     * @param userId - To get previous order items.
     * @return All previous order items.
     */
    List<OrderSystem> getUserPreviousOrder(Long userId);
}
