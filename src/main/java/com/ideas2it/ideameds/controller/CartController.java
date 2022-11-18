package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.model.Cart;
import com.ideas2it.ideameds.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class CartController {
    @Autowired
    CartService cartService;

    @PostMapping("/api/cart")
    private ResponseEntity<String> addCart(@RequestBody Cart cart) {
        Cart  addedCart = cartService.addCart(cart);
        if (addedCart != null) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Cart added successfully. Total price is : " + addedCart.getTotalPrice());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can't add");
        }
    }

    @GetMapping("/api/allcart")
    public ResponseEntity<List<Cart>> getAllCart() {
        return (ResponseEntity.status(HttpStatus.ACCEPTED).body(cartService.getAllCart()));
    }

    @GetMapping("/api/cart/{id}")
    public ResponseEntity<Cart> getById(@PathVariable("id") Long id) {
        Cart getCart = cartService.getById(id);
        if (getCart != null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(getCart);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/api/cart/{id}")
    public ResponseEntity<Cart> updateCart(@PathVariable("id") Long id, @RequestBody Cart cart) {
        Cart updatedCart = cartService.updateCart(cart);
        if (updatedCart != null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(updatedCart);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
