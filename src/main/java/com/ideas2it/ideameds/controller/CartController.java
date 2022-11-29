/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.dto.CartDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.service.CartService;
import com.ideas2it.ideameds.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * controller for cart.
 *
 * @author - Soundharrajan.S
 * @version - 1.0
 * @since - 2022-11-21
 */

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    /**
     * Medicines add in cart and save in cart repository.
     * @param userId - To map user with cart.
     * @param cartDto - To store the data in cart repository.
     * @return Total price, discount price and discount.
     */
    @PutMapping("/cart/{id}")
    public ResponseEntity<CartDTO> addCart(@PathVariable("id") Long userId, @RequestBody CartDTO cartDto) throws CustomException {
        Optional<CartDTO> cartDTO = cartService.addCart(userId, cartDto);
        if (cartDTO.isPresent() && cartDTO.get().getTotalPrice() != 0) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(cartDTO.get());
        } else {
           throw new CustomException(Constants.CAN_NOT_ADD_ITEMS_IN_CART);
        }
    }

    /**
     * Get one cart by user id.
     * @param userId - To get one cart.
     * @return One cart.
     */
    @GetMapping("/cart/{id}")
    public ResponseEntity<CartDTO> getCartByUserId(@PathVariable("id") Long userId) throws CustomException {
        CartDTO cart = cartService.getCartByUserId(userId);
        if (null != cart) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(cart);
        } else {
            throw new CustomException(Constants.USER_NOT_FOUND);
        }
    }

    /**
     * Delete cart by user id.
     * @param userId - To delete the cart by user id.
     * @return Deleted message.
     */
    @DeleteMapping("/cart/{id}")
    public ResponseEntity<String> deleteCartByUserId(@PathVariable("id") Long userId) throws CustomException {
        boolean isDelete = cartService.deleteCartByUserId(userId);
        if (isDelete) {
            return ResponseEntity
                    .status(HttpStatus.GONE)
                    .body(Constants.REMOVED_SUCCESSFULLY);
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Constants.NO_ITEMS);
    }
}
