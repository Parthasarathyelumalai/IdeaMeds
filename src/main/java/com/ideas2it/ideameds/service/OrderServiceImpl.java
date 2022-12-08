/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.BrandDTO;
import com.ideas2it.ideameds.dto.BrandItemsDTO;
import com.ideas2it.ideameds.dto.DiscountDTO;
import com.ideas2it.ideameds.dto.MedicineDTO;
import com.ideas2it.ideameds.dto.OrderItemDTO;
import com.ideas2it.ideameds.dto.OrderDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.model.Brand;
import com.ideas2it.ideameds.model.BrandItems;
import com.ideas2it.ideameds.model.Cart;
import com.ideas2it.ideameds.model.CartItem;
import com.ideas2it.ideameds.model.Discount;
import com.ideas2it.ideameds.model.Medicine;
import com.ideas2it.ideameds.model.OrderItem;
import com.ideas2it.ideameds.model.Order;
import com.ideas2it.ideameds.model.User;
import com.ideas2it.ideameds.repository.CartRepository;
import com.ideas2it.ideameds.repository.DiscountRepository;
import com.ideas2it.ideameds.repository.OrderRepository;
import com.ideas2it.ideameds.repository.UserRepository;
import com.ideas2it.ideameds.util.Constants;
import com.ideas2it.ideameds.util.DateTimeValidation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * It contains the implementation of the order service interface.
 * This class is used to add order, get all order, get order by user id, cancel order.
 *
 * @author - Soundharrajan.S
 * @version - 1.0
 * @since - 2022-11-21
 */

@Service
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;

    private final CartRepository cartRepository;

    private final OrderRepository orderRepository;

    private final DiscountRepository discountRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Create instance for the class
     *
     * @param userRepository     create instance for user repository
     * @param cartRepository     create instance for cart repository
     * @param orderRepository    create instance for order repository
     * @param discountRepository create instance for discount repository
     */
    @Autowired
    public OrderServiceImpl(UserRepository userRepository, CartRepository cartRepository, OrderRepository orderRepository, DiscountRepository discountRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.discountRepository = discountRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<OrderDTO> addOrder(Long userId) throws CustomException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Optional<Cart> cart = cartRepository.findByUser(user.get());
            if (cart.isPresent() && Objects.equals(user.get().getUserId(), cart.get().getUser().getUserId())) {
                Order order = new Order();
                List<CartItem> cartItemList = cart.get().getCartItemList();
                order.setUser(user.get());
                order.setCart(cart.get());
                order.setOrderDescription(Constants.ORDER_SUCCESSFUL);
                order.setTotalPrice(cart.get().getTotalPrice());
                order.setAmountPaid(calculateDiscount(order.getTotalPrice(), order));
                order.setOrderDate(DateTimeValidation.getDate());
                order.setCreatedAt(DateTimeValidation.getDate());
                order.setDeliveryDate(DateTimeValidation.getDate().plusDays(5));
                order.setModifiedAt(DateTimeValidation.getDate());
                order.setOrderItems(cartItemToOrderItem(cartItemList));
                Order savedOrder = orderRepository.save(order);
                OrderDTO orderDto = convertToOrderDto(savedOrder);
                return Optional.of(orderDto);
            } else throw new CustomException(HttpStatus.NOT_FOUND, Constants.CART_ITEM_NOT_FOUND);
        } else throw new CustomException(HttpStatus.NOT_FOUND, Constants.USER_NOT_FOUND);
    }

    /**
     * It takes the total price of the order and the order itself as parameters, then it finds all the discounts in the
     * database, then it checks if the total price is between 100 and 10000 or between 10000 and 100000, then it sets the
     * discount to the order, then it calculates the discount price, then it sets the deducted price to the order, then it
     * returns the total price after the discount
     *
     * @param totalPrice The total price of the order
     * @param order The order object that is being saved.
     * @return The total price after discount is being returned.
     */
    private float calculateDiscount(float totalPrice, Order order) {
        List<Discount> discountList = discountRepository.findAll();
        float afterDiscount = 0;

        for (Discount discount : discountList) {
            if ((totalPrice > 100 && totalPrice < 10000 && discount.getDiscountPercentage() == 5) || (totalPrice > 10000 && totalPrice < 100000 && discount.getDiscountPercentage() == 10)){
                order.setDiscount(discount);
                float discountPrice = (totalPrice * discount.getDiscountPercentage()) / 100;
                order.setDeductedPrice(discountPrice);
                afterDiscount = totalPrice - discountPrice;
            } else {
                afterDiscount = totalPrice;
            }
        }
        return afterDiscount;
    }

    /**
     * It converts an Order object to an OrderDTO object.
     *
     * @param order The order object that is to be converted to OrderDTO.
     * @return OrderDTO.
     * @throws CustomException - Brand item not found.
     */
    private OrderDTO convertToOrderDto(Order order) throws CustomException {
        if (null != order) {
            OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
            List<OrderItem> orderItemList = order.getOrderItems();
            orderDTO.setDiscountDTO(convertToDiscountDTO(order.getDiscount()));
            orderDTO.setOrderItemDTOList(convertToOrderItemDtoList(orderItemList));
            return orderDTO;
        } else {
            throw new CustomException(HttpStatus.NO_CONTENT, Constants.ORDER_IS_EMPTY);
        }
    }

    /**
     * If the discount is not null, then map it to a DiscountDTO object, otherwise create a new DiscountDTO object with
     * default values
     *
     * @param discount The discount object that we want to convert to a DTO.
     * @return A DiscountDTO object is being returned.
     */
    private DiscountDTO convertToDiscountDTO(Discount discount) {
        if (null != discount) {
            return modelMapper.map(discount, DiscountDTO.class);
        } else {
            DiscountDTO discountDTO = new DiscountDTO();
            discountDTO.setDiscountId(0L);
            discountDTO.setDiscountPercentage(0);
            discountDTO.setName("There is no discount available");
            discountDTO.setCouponCode("No coupon code");
            return discountDTO;
        }
    }

    /**
     * It converts a list of OrderItem objects to a list of OrderItemDTO objects.
     *
     * @param orderItemList The list of order items that we want to convert to a list of order item DTOs.
     * @return A list of OrderItemDTO objects.
     * @throws CustomException - Brand item not found.
     */
    private List<OrderItemDTO> convertToOrderItemDtoList(List<OrderItem> orderItemList) throws CustomException {
        List<OrderItemDTO> orderItemDTOList = new ArrayList<>();
        if ( null != orderItemList ) {
            for (OrderItem orderItem : orderItemList) {
                BrandItemsDTO brandItemsDTO = convertToBrandItemDto(orderItem.getBrandItems());
                OrderItemDTO orderItemDTO = modelMapper.map(orderItem, OrderItemDTO.class);
                orderItemDTO.setBrandItemsDTO(brandItemsDTO);
                orderItemDTOList.add(orderItemDTO);
            }
        } else throw new CustomException(HttpStatus.NOT_FOUND, Constants.ORDER_ITEM_NOT_FOUND);
        return orderItemDTOList;
    }

    /**
     * It converts a BrandItems object to a BrandItemsDTO object
     *
     * @param brandItems The object that is to be converted to BrandItemsDTO.
     * @return BrandItemsDTO.
     * @throws CustomException Brand item not found.
     */
    private BrandItemsDTO convertToBrandItemDto(BrandItems brandItems) throws CustomException {
        BrandItemsDTO brandItemsDTO = modelMapper.map(brandItems, BrandItemsDTO.class);
        if ( null != brandItemsDTO ) {
            MedicineDTO medicineDTO = convertToMedicineDto(brandItems.getMedicine());
            BrandDTO brandDTO = convertToBrandDto(brandItems.getBrand());
            brandItemsDTO.setBrandDTO(brandDTO);
            brandItemsDTO.setMedicineDTO(medicineDTO);
            return brandItemsDTO;
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, Constants.BRAND_ITEM_NOT_FOUND);
        }
    }

    /**
     * It converts a Brand object to a BrandDTO object
     *
     * @param brand The brand object to be converted to BrandDTO
     * @return A BrandDTO object.
     * @throws CustomException - brand not found.
     */
    private BrandDTO convertToBrandDto(Brand brand) throws CustomException {
        if ( null != brand ) {
            return modelMapper.map(brand, BrandDTO.class);
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, Constants.BRAND_NOT_FOUND);
        }
    }

    /**
     * It converts a Medicine object to a MedicineDTO object
     *
     * @param medicine The medicine object that needs to be converted to MedicineDTO.
     * @return MedicineDTO
     * @throws CustomException -  medicine not found.
     */
    public MedicineDTO convertToMedicineDto(Medicine medicine) throws CustomException {
        if ( null != medicine ) {
            return modelMapper.map(medicine, MedicineDTO.class);
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, Constants.MEDICINE_NOT_FOUND);
        }
    }

    /**
     * It takes a list of cart items and returns a list of order items
     *
     * @param cartItemList The list of cart items that are to be converted to order items.
     * @return A list of order items.
     * @throws CustomException Cart item not found.
     */
    public List<OrderItem> cartItemToOrderItem(List<CartItem> cartItemList) throws CustomException {
        List<OrderItem> orderItemList = new ArrayList<>();
        if ( cartItemList != null ) {
            for (CartItem cartItem : cartItemList) {
                OrderItem orderItem = new OrderItem();
                orderItem.setCreatedAt(DateTimeValidation.getDate());
                orderItem.setModifiedAt(DateTimeValidation.getDate());
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setBrandItems(cartItem.getBrandItems());
                orderItemList.add(orderItem);
            }
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, Constants.CART_ITEM_NOT_FOUND);
        }
        return orderItemList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OrderDTO> getAllOrder() throws CustomException {
        List<Order> orderList = orderRepository.findAll();
        List<OrderDTO> orderDTOList = new ArrayList<>();
        if ( !orderList.isEmpty() ) {
            for (Order order : orderList) {
                OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
                orderDTO.setOrderItemDTOList(convertToOrderItemDtoList(order.getOrderItems()));
                orderDTOList.add(orderDTO);
            }
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, Constants.ORDER_ITEM_NOT_FOUND);
        }
        return orderDTOList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<List<OrderDTO>> getOrderByUserId(Long userId) throws CustomException {
        List<OrderDTO> orderDTOList = new ArrayList<>();
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Optional<List<Order>> orderSystemList = orderRepository.findByUser(user.get());
            if (orderSystemList.isPresent()) {
                for (Order order : orderSystemList.get()) {
                    orderDTOList.add(convertToOrderDto(order));
                }
            } else throw new CustomException(HttpStatus.NO_CONTENT, Constants.NO_HISTORY_OF_ORDERS);
        } else throw new CustomException(HttpStatus.NO_CONTENT, Constants.USER_NOT_FOUND);
        return Optional.of(orderDTOList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean cancelOrder(Long userId, Long orderId) throws CustomException {
        Optional<User> user = userRepository.findById(userId);
        if ( user.isPresent() ) {
            Optional<Order> orderSystem = orderRepository.findById(orderId);
            if ( orderSystem.isPresent() ) {
                orderSystem.get().setUser(null);
                orderSystem.get().setOrderItems(null);
                orderRepository.deleteById(orderId);
                return true;
            } else throw new CustomException(HttpStatus.NO_CONTENT, Constants.NO_ITEM_TO_CANCEL_THE_ORDER);
        } else throw new CustomException(HttpStatus.NOT_FOUND, Constants.USER_NOT_FOUND);
    }
}