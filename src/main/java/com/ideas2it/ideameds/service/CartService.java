/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.CartDTO;
import com.ideas2it.ideameds.exception.CustomException;

import java.util.List;
import java.util.Optional;

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
     * @param cartDto - To store the data in cart repository.
     * @return Total price, discount price and discount.
     */
    Optional<CartDTO> addCart(Long userId, CartDTO cartDto) throws CustomException;

    /**
     * Get one cart by user id.
     *
     * @param userId - To get one cart.
     * @return One cart.
     */
    Optional<CartDTO> getCartByUserId(Long userId) throws CustomException;

    /**
     * Retrieve all data from cart repository.
     *
     * @return All cart.
     */
    List<CartDTO> getAllCart();

    boolean deleteCartByUserId(Long userId);
}
