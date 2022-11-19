package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.model.Cart;

import java.util.List;

public interface CartService {
    float addCart(Long userId, Cart cart);

    Cart getById(Long id);

    List<Cart> getAllCart();
}
