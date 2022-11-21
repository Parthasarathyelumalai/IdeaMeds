package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.model.Cart;

import java.util.List;

/**
 * Service for cart.
 *
 * @author - Soundharrajan.S
 * @version - 1.0
 * @since - 2022-11-21
 */

public interface CartService {

    /**
     * Medicines add in cart and save in cart repository.
     * @param userId - To map user with cart.
     * @param cart - To store the data in cart repository.
     * @return Total price, discount price and discount.
     */
    Cart addCart(Long userId, Cart cart);

    /**
     * Get one cart by user id.
     * @param userId - To get one cart.
     * @return One cart.
     */
    Cart getById(Long userId);

    /**
     * Retrieve all data from cart repository.
     * @return All cart.
     */
    List<Cart> getAllCart();
}
