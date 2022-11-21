package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.model.Cart;
import com.ideas2it.ideameds.model.CartItem;
import com.ideas2it.ideameds.model.OrderItem;
import com.ideas2it.ideameds.model.OrderSystem;
import com.ideas2it.ideameds.model.User;
import com.ideas2it.ideameds.repository.CartRepository;
import com.ideas2it.ideameds.repository.OrderSystemRepository;
import com.ideas2it.ideameds.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service implementation for placing order(order system).
 *
 * @author - Soundharrajan.S
 * @version - 1.0
 * @since - 2022-11-21
 */
@Service
public class OrderSystemServiceImpl implements OrderSystemService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderSystemRepository orderSystemRepository;

    /**
     *{@inheritDoc}
     */
    @Override
    public OrderSystem addOrder(Long userId) {
        User user = userRepository.findById(userId).get();
        List<CartItem> cartItemList = null;
        OrderSystem orderSystem =  null;
        List<Cart> cartList = cartRepository.findAll();
        for (Cart oneCart : cartList) {
            if(user.getUserId() == oneCart.getUser().getUserId()) {
                cartItemList = oneCart.getCartItemList();
                orderSystem = new OrderSystem();
                orderSystem.setUser(user);
                orderSystem.setCart(oneCart);
                orderSystem.setTotalPrice(oneCart.getTotalPrice());
                orderSystem.setDiscountPercentage(oneCart.getDiscountPercentage());
                orderSystem.setDiscountPrice(oneCart.getDiscountPrice());
                orderSystem.setDiscount(oneCart.getDiscount());
            }
        }
        if (cartItemList != null) {
            List<OrderItem> orderItemList = cartItemToOrderItem(cartItemList);
            orderSystem.setOrderItemList(orderItemList);
            orderSystemRepository.save(orderSystem);
        }
        return orderSystem;
    }

    /**
     * Copy cart item to order item.
     * @param cartItemList - To copy cart item list to order item list.
     * @return - List of order items.
     */
    public List<OrderItem> cartItemToOrderItem(List<CartItem> cartItemList) {
        List<OrderItem> orderItemList = new ArrayList<>();
        if (cartItemList != null) {
            OrderItem orderItem;
            for(CartItem cartItem : cartItemList) {
                orderItem = new OrderItem();
                orderItem.setMedicine(cartItem.getMedicine());
                orderItem.setQuantity(cartItem.getQuantity());
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
     *{@inheritDoc}
     */
    @Override
    public OrderSystem getById(Long userId) {
        User user = userRepository.findById(userId).get();
        List<OrderSystem> orderSystemList = orderSystemRepository.findAll();
        for (OrderSystem orderSystem : orderSystemList) {
            if (user.getUserId() == orderSystem.getUser().getUserId()) {
                return orderSystem;
            }
        }
        return null;
    }
}
