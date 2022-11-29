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
import com.ideas2it.ideameds.model.BrandItems;
import com.ideas2it.ideameds.model.Cart;
import com.ideas2it.ideameds.model.CartItem;
import com.ideas2it.ideameds.model.Medicine;
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
                orderSystem.setTotalPrice(cart.get().getTotalPrice());
                orderSystem.setDiscountPercentage(cart.get().getDiscountPercentage());
                orderSystem.setDiscountPrice(cart.get().getDiscountPrice());
                orderSystem.setDiscount(cart.get().getDiscount());
                orderSystem.setOrderDate(DateTimeValidation.getDate());
                orderSystem.setCreatedAt(DateTimeValidation.getDate());
                orderSystem.setModifiedAt(DateTimeValidation.getDate());
                orderSystem.setOrderItems(cartItemToOrderItem(cartItemList));
                OrderSystem savedOrder = orderSystemRepository.save(orderSystem);
                OrderSystemDTO orderDto = convertToOrderDto(savedOrder);
                return Optional.of(orderDto);
            } else throw new CustomException(Constants.CART_ITEM_NOT_FOUND);
        } else throw new CustomException(Constants.USER_NOT_FOUND);
    }

    /**
     * Convert order entity to order dto.
     * @param orderSystem - To show for user convert order entity to order dto.
     * @return - Order dto.
     * @throws CustomException - Brand item not found.
     */
    private OrderSystemDTO convertToOrderDto(OrderSystem orderSystem) throws CustomException {
        OrderSystemDTO orderSystemDTO = modelMapper.map(orderSystem, OrderSystemDTO.class);
        List<OrderItem> orderItemList = orderSystem.getOrderItems();
        orderSystemDTO.setOrderItemDTOList(convertToOrderItemDtoList(orderItemList));
        return orderSystemDTO;
    }

    /**
     * Convert order item entity to order item dto.
     * @param orderItemList - To convert order item entity to order item dto.
     * @return - List of order item dto.
     * @throws CustomException - Brand item not found.
     */
    private List<OrderItemDTO> convertToOrderItemDtoList(List<OrderItem> orderItemList) throws CustomException {
        List<OrderItemDTO> orderItemDTOList = new ArrayList<>();
        for (OrderItem orderItem : orderItemList) {
            Optional<BrandItemsDTO> brandItemsDTO = convertToBrandItemDto(orderItem.getBrandItems());
            if (brandItemsDTO.isPresent()) {
                OrderItemDTO orderItemDTO = modelMapper.map(orderItem, OrderItemDTO.class);
                orderItemDTO.setBrandItemsDTO(brandItemsDTO.get());
                orderItemDTO.setMedicineDTO(convertToMedicineDto(orderItem.getMedicine()));
                orderItemDTOList.add(orderItemDTO);
            }
        }
        return orderItemDTOList;
    }


    /**
     * Brand item entity convert into brand item dto.
     * @param brandItems - To convert brand item entity to brand item dto.
     * @return - Brand item dto.
     */
    public Optional<BrandItemsDTO> convertToBrandItemDto(BrandItems brandItems) {
        if (null != brandItems) return Optional.of(modelMapper.map(brandItems, BrandItemsDTO.class));
        return  Optional.empty();
    }

    /**
     * Convert to medicine entity to medicine dto.
     * @param medicine - Convert to medicine entity to medicine dto.
     * @return - medicine dto.
     * @throws CustomException -  medicine not found.
     */
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
                orderItem.setCreatedAt(DateTimeValidation.getDate());
                orderItem.setModifiedAt(DateTimeValidation.getDate());
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
            orderSystemDTO.setOrderItemDTOList(convertToOrderItemDtoList(orderSystem.getOrderItems()));
            orderSystemDTOList.add(orderSystemDTO);
        }
        return orderSystemDTOList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<List<OrderSystemDTO>> getOrderByUserId(Long userId) throws CustomException {
        List<OrderSystemDTO> orderSystemDTOList = new ArrayList<>();
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Optional<List<OrderSystem>> orderSystemList = orderSystemRepository.findByUser(user.get());
            if (orderSystemList.isPresent()) {
                for (OrderSystem orderSystem : orderSystemList.get()) {
                    OrderSystemDTO orderSystemDTO = convertToOrderDto(orderSystem);
                    orderSystemDTOList.add(orderSystemDTO);
                }
            } else throw new CustomException(Constants.NO_HISTORY_OF_ORDERS);
        } else throw new CustomException(Constants.USER_NOT_FOUND);
        return Optional.of(orderSystemDTOList);
    }

    /**
     *{@inheritDoc}
     */
    public Optional<List<OrderSystemDTO>> getUserPreviousOrder(Long userId) throws CustomException {
        Optional<User> user = userRepository.findById(userId);
        List<OrderSystemDTO> orderSystemDTOList = new ArrayList<>();
        if (user.isPresent()) {
            Optional<List<OrderSystem>> orderSystemList = orderSystemRepository.findByUser(user.get());
            if (orderSystemList.isPresent()) {
                for (OrderSystem orderSystem : orderSystemList.get()) {
                    OrderSystemDTO orderSystemDTO = convertToOrderDto(orderSystem);
                    orderSystemDTOList.add(orderSystemDTO);
                }
            } else throw new CustomException(Constants.NO_HISTORY_OF_ORDERS);
        } else throw new CustomException(Constants.USER_NOT_FOUND);
        return Optional.of(orderSystemDTOList);
    }

    /**
     * Cancel the order by user id.
     * @param userId - To get user from repository.
     * @return - boolean
     * @throws CustomException - User not found, Order not found.
     */
    @Override
    public boolean cancelOrder(Long userId) throws CustomException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent() && !user.get().isDeletedStatus()) {
            Optional<List<OrderSystem>> orderSystemList = orderSystemRepository.findByUser(user.get());
            if (orderSystemList.isPresent()) {
                for (OrderSystem historyOfOrder : orderSystemList.get()) {
                    orderSystemRepository.deleteById(historyOfOrder.getOrderId());
                }
                return true;
            } else throw new CustomException(Constants.NO_ITEM_TO_CANCEL_THE_ORDER);
        } throw new CustomException(Constants.USER_NOT_FOUND);
    }
}
