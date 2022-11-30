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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * This cart controller is used to manipulate the cart service and cart service implementation.
 *
 * @author - Soundharrajan.S
 * @version - 1.0
 * @since - 2022-11-30
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
     * @throws CustomException - Can not add item in cart.
     */
    @PutMapping("/cart/{id}")
    public ResponseEntity<CartDTO> addCart(@PathVariable("id") Long userId, @RequestBody CartDTO cartDto) throws CustomException {
        Optional<CartDTO> cartDTO = cartService.addCart(userId, cartDto);
        if (cartDTO.isPresent() && cartDTO.get().getTotalPrice() != 0) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(cartDTO.get());
        } else throw new CustomException(Constants.CAN_NOT_ADD_ITEMS_IN_CART);
    }

    /**
     * Get cart by user id.
     * @param userId - To get one cart.
     * @return One cart.
     * @throws CustomException - Cart item not found.
     */
    @GetMapping("/cart/{id}")
    public ResponseEntity<CartDTO> getCartByUserId(@PathVariable("id") Long userId) throws CustomException {
        Optional<CartDTO> cartDTO = cartService.getCartByUserId(userId);
        if (cartDTO.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(cartDTO.get());
        } else throw new CustomException(Constants.CART_ITEM_NOT_FOUND);
    }

    /**
     * Delete cart by user id.
     * @param userId - To delete the cart by user id.
     * @return Deleted message.
     * @throws CustomException - Can not delete items in cart.
     */
    @DeleteMapping("/cart/{id}")
    public ResponseEntity<String> deleteCartByUserId(@PathVariable("id") Long userId) throws CustomException {
        boolean isDelete = cartService.deleteCartByUserId(userId);
        if (isDelete) {
            return ResponseEntity
                    .status(HttpStatus.GONE)
                    .body(Constants.REMOVED_SUCCESSFULLY);
        } else throw new CustomException(Constants.NOT_DELETED_SUCCESSFULLY);
    }
}
