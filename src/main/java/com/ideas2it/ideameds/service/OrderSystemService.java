package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.model.OrderSystem;
import org.hibernate.criterion.Order;

import java.util.List;

public interface OrderSystemService {

    /**
     * Add order items in repository.
     * @param userId - To get user and cart details and map with order.
     * @return - Total price of order.
     */
    float addOrder(Long userId);

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
