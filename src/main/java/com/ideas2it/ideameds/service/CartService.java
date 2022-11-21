package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.model.Cart;

import java.util.List;

public interface CartService {

    /**
     * Medicines add in cart using id, cart details.
     * @param userId - To map user with cart.
     * @param cart - To store all the cart data in repository.
     * @return Total price of the cart.
     */
    float addCart(Long userId, Cart cart);

    /**
     * Get one cart by cart id.
     * @param userId - To get one cart.
     * @return One cart.
     */
    Cart getById(Long userId);

    /**
     * Retrieve all data.
     * @return All carts.
     */
    List<Cart> getAllCart();
}
