/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.model.Cart;
import com.ideas2it.ideameds.model.CartItem;
import com.ideas2it.ideameds.model.OrderItem;
import com.ideas2it.ideameds.model.OrderSystem;
import com.ideas2it.ideameds.model.User;
import com.ideas2it.ideameds.repository.CartRepository;
import com.ideas2it.ideameds.repository.OrderSystemRepository;
import com.ideas2it.ideameds.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Service implementation for placing order(order system).
 *
 * @author - Soundharrajan.S
 * @version - 1.0
 * @since - 2022-11-21
 */
@Service
@RequiredArgsConstructor
public class OrderSystemServiceImpl implements OrderSystemService {

    private final UserRepository userRepository;

    private final CartRepository cartRepository;

    private final OrderSystemRepository orderSystemRepository;


    /**
     *{@inheritDoc}
     */
    @Override
    public Optional<OrderSystem> addOrder(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        List<CartItem> cartItemList;
        OrderSystem orderSystem = null;
        List<Cart> cartList = cartRepository.findAll();
        if (user.isPresent()) {
            for (Cart cart : cartList) {
                if(Objects.equals(user.get().getUserId(), cart.getUser().getUserId())) {
                    orderSystem =  new OrderSystem();
                    cartItemList = cart.getCartItemList();
                    orderSystem.setUser(user.get());
                    orderSystem.setCart(cart);
                    orderSystem.setTotalPrice(cart.getTotalPrice());
                    orderSystem.setDiscountPercentage(cart.getDiscountPercentage());
                    orderSystem.setDiscountPrice(cart.getDiscountPrice());
                    orderSystem.setDiscount(cart.getDiscount());
                    orderSystem.setOrderItemList(cartItemToOrderItem(cartItemList));
                    orderSystemRepository.save(orderSystem);
                }
            }
        }
        return Optional.ofNullable(orderSystem);
    }

    /**
     * Copy cart item to order item.
     * @param cartItemList - To copy cart item list to order item list.
     * @return - List of order items.
     */
    public List<OrderItem> cartItemToOrderItem(List<CartItem> cartItemList) {
        List<OrderItem> orderItemList = new ArrayList<>();
        if (cartItemList != null) {
            for(CartItem cartItem : cartItemList) {
                OrderItem orderItem  = new OrderItem();
                orderItem.setMedicine(cartItem.getMedicine());
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setBrandItems(cartItem.getBrandItems());
                orderItemList.add(orderItem);
            }
        }
        return orderItemList;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public List<OrderSystem> getAllOrder() {
        return orderSystemRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<OrderSystem> getOrderByUserId(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Optional<OrderSystem> orderSystem = orderSystemRepository.findByUser(user.get());
            if (orderSystem.isPresent()) {
                return orderSystem;
            }
        }
        return Optional.empty();
    }

    /**
     *{@inheritDoc}
     */
    public List<OrderSystem> getUserPreviousOrder(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        List<OrderSystem> orderSystemList = orderSystemRepository.findAll();
        List<OrderSystem> previousOrder = new ArrayList<>();
        for (OrderSystem orderSystem : orderSystemList) {
            if (Objects.equals(user.get().getUserId(), orderSystem.getUser().getUserId())) {
                previousOrder.add(orderSystem);
            }
        }
        return previousOrder;
    }
}
