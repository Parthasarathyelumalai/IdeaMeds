/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.dto.CartDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.service.CartService;
import com.ideas2it.ideameds.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CartController {

    private final CartService cartService;

    /**
     * Create instance for the class
     *
     * @param cartService - to create instance for cart service
     */
    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }


    /**
     * It adds items to the cart of a user.
     *
     * @param userId The id of the user whose cart is to be updated.
     * @param cartDto This is the object that contains the product id and quantity of the product.
     * @return ResponseEntity<CartDTO>
     * @throws CustomException User not found, Cart not found, Brand not found,
     *                         Brand item not found, Medicine not found.
     */
    @PutMapping("/cart/{id}")
    public ResponseEntity<CartDTO> addCart(@PathVariable("id") Long userId, @RequestBody CartDTO cartDto) throws CustomException {
        Optional<CartDTO> cartDTO = cartService.addCart(userId, cartDto);
        if (cartDTO.isPresent() && cartDTO.get().getTotalPrice() != 0) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(cartDTO.get());
        } else throw new CustomException(HttpStatus.NOT_FOUND, Constants.CAN_NOT_ADD_ITEMS_IN_CART);
    }


    /**
     * It returns a cart object for a given user id.
     *
     * @param userId The id of the user whose cart is to be retrieved.
     * @return A cartDTO object.
     * @throws CustomException User not found, Cart not found.
     */
    @GetMapping("/cart/{id}")
    public ResponseEntity<CartDTO> getCartByUserId(@PathVariable("id") Long userId) throws CustomException {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(cartService.getCartByUserId(userId));
    }

    /**
     * It deletes the cart of a user.
     *
     * @param userId The userId of the user whose cart is to be deleted.
     * @return ResponseEntity<String>
     * @throws CustomException User not found, Cart not found.
     */
    @DeleteMapping("/cart/{id}")
    public ResponseEntity<String> deleteCartByUserId(@PathVariable("id") Long userId) throws CustomException {
        boolean isDelete = cartService.deleteCartByUserId(userId);
        if (isDelete) {
            return ResponseEntity
                    .status(HttpStatus.GONE)
                    .body(Constants.REMOVED_SUCCESSFULLY);
        } else throw new CustomException(HttpStatus.NOT_FOUND,Constants.NOT_DELETED_SUCCESSFULLY);
    }
}
