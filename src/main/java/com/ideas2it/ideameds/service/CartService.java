/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.CartDTO;
import com.ideas2it.ideameds.exception.CustomException;

import java.util.Optional;

/**
 * This is a service interface for cart.
 *
 * @author - Soundharrajan S.
 * @version - 1.0
 * @since - 2022-11-30
 */

public interface CartService {

    /**
     * Add a cart to the user's account.
     *
     * @param userId The userId of the user who is adding the cart.
     * @param cartDto The cart object that needs to be added to the database.
     * @return Optional<CartDTO>
     * @throws CustomException User not found, Cart not found, Brand not found,
     *                           Brand item not found, Medicine not found.
     */
    Optional<CartDTO> addCart(Long userId, CartDTO cartDto) throws CustomException;

    /**
     * Get the cart of a user by user id
     *
     * @param userId The userId of the user whose cart is to be fetched.
     * @return CartDTO
     * @throws CustomException User not found, Cart not found.
     */
    CartDTO getCartByUserId(Long userId) throws CustomException;

    /**
     * Delete a cart by user id
     *
     * @param userId The user id of the user whose cart is to be deleted.
     * @return boolean
     * @throws CustomException User not found, Cart not found.
     */
    boolean deleteCartByUserId(Long userId) throws CustomException;
}
