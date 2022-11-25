/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.model.BrandItems;
import com.ideas2it.ideameds.model.Cart;
import com.ideas2it.ideameds.model.CartItem;
import com.ideas2it.ideameds.model.Discount;
import com.ideas2it.ideameds.model.User;
import com.ideas2it.ideameds.repository.BrandItemsRepository;
import com.ideas2it.ideameds.repository.CartRepository;
import com.ideas2it.ideameds.repository.DiscountRepository;
import com.ideas2it.ideameds.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Service implementation for cart.
 *
 * @author - Soundharrajan.S
 * @version - 1.0
 * @since - 2022-11-21
 */

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final UserRepository userRepository;

    private final DiscountRepository discountRepository;

    private final BrandItemsRepository brandItemsRepository;

    /**
     *{@inheritDoc}
     */
    @Override
    public Optional<Cart> addCart(Long userId, Cart cart) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Optional<Cart> existedCart = cartRepository.findByUser(user.get());
            if(existedCart.isPresent()) {
                cart.setCartId(existedCart.get().getCartId());
                cart.setUser(user.get());
                existedCart = Optional.of(cart);
                return Optional.ofNullable(checkAndAddCart(existedCart.get()));
            } else {
                cart.setUser(user.get());
                return Optional.ofNullable(checkAndAddCart(cart));
            }
        }
        return Optional.empty();
    }

    /**
     * This method is used to avoid cart id duplicate.
     * Set details of new cart and existed cart.
     *
     * @param cart - Set all the cart details in the cart.
     * @return cart.
     */
    public Cart checkAndAddCart(Cart cart) {
        float price = 0;
        List<CartItem> cartItems = new ArrayList<>();
        List<CartItem> userCartItems = cart.getCartItemList();
        for(CartItem cartItemTemp : userCartItems) {
            Optional<BrandItems> brandItems = brandItemsRepository.findById(cartItemTemp.getBrandItems().getBrandItemsId());
            if (brandItems.isPresent()) {
                CartItem cartItem = new CartItem();
                cartItem.setQuantity(cartItemTemp.getQuantity());
                cartItem.setBrandItems(brandItems.get());
                cartItem.setMedicine(brandItems.get().getMedicine());
                cartItems.add(cartItem);
                price = price + (brandItems.get().getPrice() * cartItem.getQuantity());
            }
        }
        cart.setCartItemList(cartItems);
        cart.setTotalPrice(price);
        price = calculateDiscount(price, cart);
        cart.setDiscountPrice(price);
        return cartRepository.save(cart);
    }

    /**
     * Calculate discount by total price of the medicines(cart items).
     * Set discount related details in cart - discount, discount percentage, discount price.
     *
     * @param price - To calculate suitable discount.
     * @param cart - To set discount details in cart.
     * @return price - after calculate discount.
     */
    public float calculateDiscount(float price, Cart cart) {
        List<Discount> discountList = discountRepository.findAll();
        float afterDiscount = 0;
        for (Discount discount : discountList) {
            if ((price > 100 && price < 1000 && discount.getDiscount() == 5) || (price > 1000 && price < 2000 && discount.getDiscount() == 10)){
                cart.setDiscount(discount);
                cart.setDiscountPercentage(discount.getDiscount());
                float discountPrice = (price * discount.getDiscount()) / 100;
                afterDiscount = price - discountPrice;
            } else {
                afterDiscount = price;
            }
        }
        return afterDiscount;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Optional<Cart> getCartByUserId(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return cartRepository.findByUser(user.get());
        }
        return Optional.empty();
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public List<Cart> getAllCart() {
        return cartRepository.findAll();
    }

    /**
     * Delete user cart by user id.
     *
     * @param userId - To get user and cart.
     * @return boolean.
     */
    @Override
    public boolean deleteCartByUserId(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Optional<Cart> cart = cartRepository.findByUser(user.get());
            cart.ifPresent(value -> cartRepository.deleteById(value.getCartId()));
            return true;
        }
        return false;
    }
}
