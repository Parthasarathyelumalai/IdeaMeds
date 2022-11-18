package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.model.Cart;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartService {
    Cart addCart(Cart cart);

    Cart getById(Long id);

    Cart updateCart(Cart cart);

    List<Cart> getAllCart();
}
