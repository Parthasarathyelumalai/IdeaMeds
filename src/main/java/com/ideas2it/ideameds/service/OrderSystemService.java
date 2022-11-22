package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.model.OrderSystem;

import java.util.List;

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
     * @param userId - To get user and cart details to map with order.
     * @return - Total price of order.
     */
    OrderSystem addOrder(Long userId);

    /**
     * All users order details.
     * @return - All users order details.
     */
    List<OrderSystem> getAllOrder();

    /**
     * To get one order details by user id.
     * @param userId - To get one user order.
     * @return - One user order details
     */
    OrderSystem getById(Long userId);
}
