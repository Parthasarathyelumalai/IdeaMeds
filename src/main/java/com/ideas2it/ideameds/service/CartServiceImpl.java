package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.model.Cart;
import com.ideas2it.ideameds.model.CartItem;
import com.ideas2it.ideameds.model.Medicine;
import com.ideas2it.ideameds.model.User;
import com.ideas2it.ideameds.repository.CartRepository;
import com.ideas2it.ideameds.repository.MedicineRepository;
import com.ideas2it.ideameds.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Override
    public float addCart(Long userId, Cart cart) {
        User user = userRepository.findById(userId).get();
        float price = 0;
        if (user.getUserId() != null) {
            cart.setUser(user);
            for(CartItem cartItem: cart.getCartItemList()) {
                Medicine medicine = cartItem.getMedicine();
                if (medicine != null) {
                    Medicine medicine1 = medicineRepository.findById(medicine.getMedicineId()).get();
                    price = price + medicine1.getPrice();
                } else {
                    return 0;
                }
            }
        }
        cart.setTotalPrice(price);
        cartRepository.save(cart);
        return price;
    }

    @Override
    public Cart getById(Long id) {
        if (cartRepository.findById(id).get() != null) {
            return cartRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public List<Cart> getAllCart() {
        return cartRepository.findAll();
    }
}
