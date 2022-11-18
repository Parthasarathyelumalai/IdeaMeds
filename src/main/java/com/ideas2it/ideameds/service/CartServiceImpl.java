package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.model.Cart;
import com.ideas2it.ideameds.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public Cart addCart(Cart cart) {
        if (ValidateCart(cart)) {
            Cart addedCart = cartRepository.save(cart);
            return (addedCart);
        }
        return null;
    }

    @Override
    public Cart getById(Long id) {
        if (cartRepository.findById(id) != null) {
            return cartRepository.findById(id).get();
        } else {
            return null;
        }

    }

    @Override
    public Cart updateCart(Cart cart) {
        if (ValidateCart(cart)) {
            Cart updatedCart = cartRepository.save(cart);
            return (updatedCart);
        }
        return null;
    }

    @Override
    public List<Cart> getAllCart() {
        return cartRepository.findAll();
    }

    public boolean ValidateCart(Cart cart) {
        if(cart.getCartId() != null) {
            return true;
        } else {
            return false;
        }
    }
}
