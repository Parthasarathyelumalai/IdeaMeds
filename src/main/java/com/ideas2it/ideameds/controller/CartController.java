package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.model.Cart;
import com.ideas2it.ideameds.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * controller for cart.
 *
 * @author - Soundharrajan.S
 * @version - 1.0
 * @since - 2022-11-17
 */

@RestController
public class CartController {
    @Autowired
    CartService cartService;

    /**
     * Medicines add in cart using id, cart details.
     * @param userId - To map user with cart.
     * @param cart - To store all the cart data in repository.
     * @return Total price of the cart.
     */
    @PutMapping("/cart/{id}")
    private ResponseEntity<String> addCart(@PathVariable("id") Long userId, @RequestBody Cart cart) {
        float cartTotalPrice = cartService.addCart(userId, cart);
        if (cartTotalPrice > 0) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Cart added successfully. Total price is : " + cartTotalPrice);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No items to list price");
        }
    }

    /**
     * Retrieve all data.
     * @return All carts.
     */
    @GetMapping("/allcart")
    public ResponseEntity<List<Cart>> getAllCart() {
        return (ResponseEntity.status(HttpStatus.ACCEPTED).body(cartService.getAllCart()));
    }

    /**
     * Get one cart by cart id.
     * @param userId - To get one cart.
     * @return One cart.
     */
    @GetMapping("/cart/{id}")
    public ResponseEntity<Cart> getById(@PathVariable("id") Long userId) {
        Cart cart = cartService.getById(userId);
        if (cart != null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(cart);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
