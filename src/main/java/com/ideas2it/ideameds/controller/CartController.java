package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.model.Cart;
import com.ideas2it.ideameds.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    CartService cartService;

    /**
     * Medicines add in cart and save in cart repository.
     * @param userId - To map user with cart.
     * @param cart - To store the data in cart repository.
     * @return Total price, discount price and discount.
     */
    @PutMapping("/cart/{id}")
    public ResponseEntity<String> addCart(@PathVariable("id") Long userId, @RequestBody Cart cart) {
        Optional<Cart> addedCart = cartService.addCart(userId, cart);
        return addedCart.map(value -> ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Cart added successfully. \nTotal price : " + value.getTotalPrice()
                        + "\nDiscount : " + value.getDiscountPercentage() + "%"
                        + "\nDiscount price : " + value.getDiscountPrice())).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("No items to list price"));
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
        Optional<Cart> cart = cartService.getById(userId);
        if (cart.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(cart.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
