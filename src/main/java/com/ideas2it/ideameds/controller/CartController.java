/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.dto.CartDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.model.Cart;
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
    public ResponseEntity<String> addCart(@PathVariable("id") Long userId, @RequestBody CartDTO cartDto) throws CustomException {
        Optional<CartDTO> addedCart = cartService.addCart(userId, cartDto);
        if (addedCart.get().getTotalPrice() != 0) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Cart added successfully. \nTotal price : " + addedCart.get().getTotalPrice()
                            + "\nDiscount : " + addedCart.get().getDiscountPercentage() + "%"
                            + "\nDiscount price : " + addedCart.get().getDiscountPrice());
        } else {
           throw new CustomException(Constants.CAN_NOT_ADD_ITEMS_IN_CART);
        }
    }

    /**
     * Retrieve all data from cart repository.
     * @return All cart.
     */
    @GetMapping("/allcart")
    public ResponseEntity<List<CartDTO>> getAllCart() {
        return (ResponseEntity.status(HttpStatus.ACCEPTED).body(cartService.getAllCart()));
    }

    /**
     * Get one cart by user id.
     * @param userId - To get one cart.
     * @return One cart.
     */
    @GetMapping("/cart/{id}")
    public ResponseEntity<CartDTO> getCartByUserId(@PathVariable("id") Long userId) throws CustomException {
        Optional<CartDTO> cart = cartService.getCartByUserId(userId);
        if (cart.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(cart.get());
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
    public ResponseEntity<String> deleteCartByUserId(@PathVariable("id") Long userId) {
        boolean deletedMessage = cartService.deleteCartByUserId(userId);
        if (deletedMessage) {
            return ResponseEntity
                    .status(HttpStatus.GONE)
                    .body("Deleted successfully");
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("No cart available");
    }
}
