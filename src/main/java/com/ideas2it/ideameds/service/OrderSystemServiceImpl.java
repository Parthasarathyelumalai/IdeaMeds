/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.BrandItemsDTO;
import com.ideas2it.ideameds.dto.MedicineDTO;
import com.ideas2it.ideameds.dto.OrderItemDTO;
import com.ideas2it.ideameds.dto.OrderSystemDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.model.*;
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

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<OrderSystemDTO> addOrder(Long userId) throws CustomException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Optional<Cart> cart = cartRepository.findByUser(user.get());
            if(cart.isPresent() && Objects.equals(user.get().getUserId(), cart.get().getUser().getUserId())) {
                OrderSystem orderSystem =  new OrderSystem();
                List<CartItem> cartItemList = cart.get().getCartItemList();
                orderSystem.setUser(user.get());
                orderSystem.setCart(cart.get());
                orderSystem.setTotalPrice(cart.get().getTotalPrice());
                orderSystem.setDiscountPercentage(cart.get().getDiscountPercentage());
                orderSystem.setDiscountPrice(cart.get().getDiscountPrice());
                orderSystem.setDiscount(cart.get().getDiscount());
                orderSystem.setOrderDate(DateTimeValidation.getDate());
                orderSystem.setCreatedAt(DateTimeValidation.getDate());
                orderSystem.setModifiedAt(DateTimeValidation.getDate());
                orderSystem.setOrderItemList(cartItemToOrderItem(cartItemList));
                OrderSystem savedOrder = orderSystemRepository.save(orderSystem);
                OrderSystemDTO orderDto = convertToOrderDto(savedOrder);
                return Optional.of(orderDto);
            } throw new CustomException(Constants.CART_ITEM_NOT_FOUND);
        } throw new CustomException(Constants.USER_NOT_FOUND);
    }

    private OrderSystemDTO convertToOrderDto(OrderSystem savedOrder) throws CustomException {
        OrderSystemDTO orderSystemDTO = modelMapper.map(savedOrder, OrderSystemDTO.class);
        List<OrderItem> orderItemList = savedOrder.getOrderItemList();
        orderSystemDTO.setOrderItemDTOList(convertToOrderItemDto(orderItemList));
        return orderSystemDTO;
    }

    private List<OrderItemDTO> convertToOrderItemDto(List<OrderItem> orderItemList) throws CustomException {
        List<OrderItemDTO> orderItemDTOList = new ArrayList<>();
        for (OrderItem orderItem : orderItemList) {
            OrderItemDTO orderItemDTO = modelMapper.map(orderItem, OrderItemDTO.class);
            Optional<BrandItemsDTO> brandItemsDTO = convertToBrandItemDto(orderItem.getBrandItems());
            if (brandItemsDTO.isPresent()) {
                orderItemDTO.setBrandItemsDTO(brandItemsDTO.get());
                orderItemDTO.setMedicineDTO(convertToMedicineDto(orderItem.getMedicine()));
                orderItemDTOList.add(orderItemDTO);
            } else throw new CustomException(Constants.BRAND_ITEM_NOT_FOUND);
        }
        return orderItemDTOList;
    }

    public Optional<BrandItemsDTO> convertToBrandItemDto(BrandItems brandItems) {
        if (null != brandItems) return Optional.of(modelMapper.map(brandItems, BrandItemsDTO.class));
        return  Optional.empty();
    }

    public MedicineDTO convertToMedicineDto(Medicine medicine) throws CustomException {
        if (null != medicine) return modelMapper.map(medicine, MedicineDTO.class);
        else throw new CustomException(Constants.MEDICINE_NOT_FOUND);
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
    public List<OrderSystemDTO> getAllOrder() throws CustomException {
        List<OrderSystem> orderSystemList = orderSystemRepository.findAll();
        List<OrderSystemDTO> orderSystemDTOList = new ArrayList<>();
        for (OrderSystem orderSystem : orderSystemList) {
            OrderSystemDTO orderSystemDTO = modelMapper.map(orderSystem, OrderSystemDTO.class);
            orderSystemDTO.setOrderItemDTOList(convertToOrderItemDto(orderSystem.getOrderItemList()));
            orderSystemDTOList.add(orderSystemDTO);
        }
        return orderSystemDTOList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<OrderSystemDTO> getOrderByUserId(Long userId) throws CustomException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Optional<OrderSystem> orderSystem = orderSystemRepository.findByUser(user.get());
            if (orderSystem.isPresent()) {
                OrderSystemDTO orderSystemDTO = convertToOrderDto(orderSystem.get());
                return Optional.of(orderSystemDTO);
            }
        } else throw new CustomException(Constants.USER_NOT_FOUND);
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
            if (user.isPresent() && Objects.equals(user.get().getUserId(), orderSystem.getUser().getUserId())) {
                previousOrder.add(orderSystem);
            }
        }
        return previousOrder;
    }


}
