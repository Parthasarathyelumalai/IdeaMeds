package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.model.*;
import com.ideas2it.ideameds.repository.CartRepository;
import com.ideas2it.ideameds.repository.MedicineRepository;
import com.ideas2it.ideameds.repository.OrderSystemRepository;
import com.ideas2it.ideameds.repository.UserRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderSystemServiceImpl implements OrderSystemService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderSystemRepository orderSystemRepository;

    @Override
    public float addOrder(Long userId) {
        User user = userRepository.findById(userId).get();
        List<CartItem> cartItemList = null;
        OrderSystem orderSystem =  null;
        float price = 0;
        List<Cart> cartList = cartRepository.findAll();
        for (Cart oneCart : cartList) {
            if(user.getUserId() == oneCart.getUser().getUserId()) {
                cartItemList = oneCart.getCartItemList();
                orderSystem = new OrderSystem();
                orderSystem.setTotalPrice(oneCart.getTotalPrice());
                orderSystem.setCart(oneCart);
                orderSystem.setUser(user);
                price = (float) orderSystem.getTotalPrice();
            }
        }
        if (cartItemList != null) {
            List<OrderItem> orderItemList = cartItemToOrderItem(cartItemList);
            orderSystem.setOrderItemList(orderItemList);
            orderSystemRepository.save(orderSystem);
        }
        return price;
    }

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

    @Override
    public List<OrderSystem> getAllOrder() {
        return orderSystemRepository.findAll();
    }

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
