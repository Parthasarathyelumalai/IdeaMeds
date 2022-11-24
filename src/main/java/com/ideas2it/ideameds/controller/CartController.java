package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.model.Cart;
import com.ideas2it.ideameds.service.CartService;
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
     * @param cart - To store the data in cart repository.
     * @return Total price, discount price and discount.
     */
    @PutMapping("/cart/{id}")
    public ResponseEntity<String> addCart(@PathVariable("id") Long userId, @RequestBody Cart cart) {
        Optional<Cart> addedCart = cartService.addCart(userId, cart);
        if (addedCart.get().getTotalPrice() != 0) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Cart added successfully. \nTotal price : " + addedCart.get().getTotalPrice()
                            + "\nDiscount : " + addedCart.get().getDiscountPercentage() + "%"
                            + "\nDiscount price : " + addedCart.get().getDiscountPrice());
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
    public ResponseEntity<Cart> getCartByUserId(@PathVariable("id") Long userId) {
        Optional<Cart> cart = cartService.getCartByUserId(userId);
        if (cart.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(cart.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

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
