/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.OrderSystemDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.model.Cart;
import com.ideas2it.ideameds.model.CartItem;
import com.ideas2it.ideameds.model.OrderItem;
import com.ideas2it.ideameds.model.OrderSystem;
import com.ideas2it.ideameds.model.User;
import com.ideas2it.ideameds.repository.CartRepository;
import com.ideas2it.ideameds.repository.OrderSystemRepository;
import com.ideas2it.ideameds.repository.UserRepository;
import com.ideas2it.ideameds.util.Constants;
import com.ideas2it.ideameds.util.DateTimeValidation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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

    private final DateTimeValidation dateTimeValidation;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<OrderSystemDTO> addOrder(Long userId) throws CustomException {
        Optional<User> user = userRepository.findById(userId);
        List<CartItem> cartItemList;
        OrderSystem orderSystem;
        if (user.isPresent()) {
            Optional<Cart> cart = cartRepository.findByUser(user.get());
            if(cart.isPresent() && Objects.equals(user.get().getUserId(), cart.get().getUser().getUserId())) {
                orderSystem =  new OrderSystem();
                cartItemList = cart.get().getCartItemList();
                orderSystem.setUser(user.get());
                orderSystem.setCart(cart.get());
                orderSystem.setTotalPrice(cart.get().getTotalPrice());
                orderSystem.setDiscountPercentage(cart.get().getDiscountPercentage());
                orderSystem.setDiscountPrice(cart.get().getDiscountPrice());
                orderSystem.setDiscount(cart.get().getDiscount());
                orderSystem.setOrderDate(dateTimeValidation.getDate());
                orderSystem.setCreatedAt(dateTimeValidation.getDate());
                orderSystem.setModifiedAt(dateTimeValidation.getDate());
                orderSystem.setOrderItemList(cartItemToOrderItem(cartItemList));
                orderSystemRepository.save(orderSystem);
                return Optional.of(modelMapper.map(orderSystemRepository.save(orderSystem), OrderSystemDTO.class));
            } throw new CustomException(Constants.CART_ITEM_NOT_FOUND);
        } throw new CustomException(Constants.USER_NOT_FOUND);
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
