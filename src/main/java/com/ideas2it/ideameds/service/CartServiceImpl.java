/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.controller.BrandController;
import com.ideas2it.ideameds.controller.BrandItemsController;
import com.ideas2it.ideameds.controller.UserController;
import com.ideas2it.ideameds.dto.BrandItemsDTO;
import com.ideas2it.ideameds.dto.CartDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.model.*;
import com.ideas2it.ideameds.repository.BrandItemsRepository;
import com.ideas2it.ideameds.repository.CartRepository;
import com.ideas2it.ideameds.repository.DiscountRepository;
import com.ideas2it.ideameds.repository.UserRepository;
import com.ideas2it.ideameds.util.Constants;
import com.ideas2it.ideameds.util.DateTimeValidation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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

    private final DateTimeValidation dateTimeValidation;

    private final UserController userController;

    private final BrandItemsController brandItemsController;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     *{@inheritDoc}
     */
    @Override
    public Optional<CartDTO> addCart(Long userId, CartDTO cartDto) throws CustomException {
        Cart cart = modelMapper.map(cartDto, Cart.class);
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Optional<Cart> existedCart = cartRepository.findByUser(user.get());
            if(existedCart.isPresent()) {
                cart.setCartId(existedCart.get().getCartId());
                cart.setUser(user.get());
                cart.setCreatedAt(dateTimeValidation.getDate());
                cart.setModifiedAt(dateTimeValidation.getDate());
                existedCart = Optional.of(cart);
                Cart addedCart = checkAndAddCart(existedCart.get());
                return Optional.of(modelMapper.map(cartRepository.save(addedCart), CartDTO.class));
            } else {
                cart.setUser(user.get());
                Cart addedCart = checkAndAddCart(cart);
                return Optional.of(modelMapper.map(cartRepository.save(addedCart), CartDTO.class));
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
    public Cart checkAndAddCart(Cart cart) throws CustomException {
        float price = 0;
        List<CartItem> cartItems = new ArrayList<>();
        List<CartItem> userCartItems = cart.getCartItemList();

        for(CartItem cartItemTemp : userCartItems) {
            BrandItemsDTO brandItemsDTO = brandItemsController.getBrandItemById(cartItemTemp.getBrandItems().getBrandItemsId()).getBody();
            Optional<BrandItems> brandItems = Optional.ofNullable(modelMapper.map(brandItemsDTO, BrandItems.class));
            if (brandItems.isPresent()) {
                CartItem cartItem = new CartItem();
                cartItem.setQuantity(cartItemTemp.getQuantity());
                cartItem.setBrandItems(brandItems.get());
                cartItem.setMedicine(brandItems.get().getMedicine());
                cartItems.add(cartItem);
                price = price + (brandItems.get().getPrice() * cartItem.getQuantity());
            } else {
                throw new CustomException(Constants.BRAND_ITEM_NOT_FOUND);
            }
        }
        cart.setCartItemList(cartItems);
        cart.setTotalPrice(price);
        price = calculateDiscount(price, cart);
        cart.setDiscountPrice(price);
        return cart;
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
     * {@inheritDoc}
     */
    @Override
    public Optional<CartDTO> getCartByUserId(Long userId) throws CustomException {
        Optional<User> user = Optional.ofNullable(userController.getUserById(userId).getBody());
        if (user.isPresent()) {
            Optional<Cart> cart = cartRepository.findByUser(user.get());
            if (cart.isPresent()) {
                return Optional.of(modelMapper.map(cart, CartDTO.class));
            } else {
                throw new CustomException(Constants.NO_ITEMS);
            }
        }
        throw new CustomException(Constants.USER_NOT_FOUND);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CartDTO> getAllCart() {
        List<CartDTO> cartList = new ArrayList<>();
        List<Cart> carts = cartRepository.findAll();
        for (Cart cart : carts) {
            cartList.add(modelMapper.map(cart, CartDTO.class));
        }
        return cartList;
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
