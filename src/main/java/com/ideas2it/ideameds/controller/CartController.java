package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.model.Cart;
import com.ideas2it.ideameds.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * controller for cart.
 *
 * @author - Soundharrajan.S
 * @version - 1.0
 * @since - 2022-11-21
 */

@RestController
public class CartController {
    @Autowired
    CartService cartService;

    /**
     * Medicines add in cart and save in cart repository.
     * @param userId - To map user with cart.
     * @param cart - To store the data in cart repository.
     * @return Total price, discount price and discount.
     */
    @PutMapping("/cart/{id}")
    private ResponseEntity<String> addCart(@PathVariable("id") Long userId, @RequestBody Cart cart) {
        Cart addedCart = cartService.addCart(userId, cart);
        if (addedCart != null) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Cart added successfully. \nTotal price : " + addedCart.getTotalPrice()
                            + "\nDiscount : " + addedCart.getDiscountPercentage()
                            + "\nDiscount price : " + addedCart.getDiscountPrice());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No items to list price");
        }
    }

    /**
     * Retrieve all data from cart repository.
     * @return All cart.
     */
    @GetMapping("/allcart")
    public ResponseEntity<List<Cart>> getAllCart() {
        return (ResponseEntity.status(HttpStatus.ACCEPTED).body(cartService.getAllCart()));
    }

    /**
     * Get one cart by user id.
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
