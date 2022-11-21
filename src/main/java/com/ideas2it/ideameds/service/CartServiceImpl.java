package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.model.*;
import com.ideas2it.ideameds.repository.CartRepository;
import com.ideas2it.ideameds.repository.DiscountRepository;
import com.ideas2it.ideameds.repository.MedicineRepository;
import com.ideas2it.ideameds.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Service implementation for cart.
 *
 * @author - Soundharrajan.S
 * @version - 1.0
 * @since - 2022-11-21
 */

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private DiscountRepository discountRepository;

    /**
     *{@inheritDoc}
     */
    @Override
    public Cart addCart(Long userId, Cart cart) {
        User user = userRepository.findById(userId).get();
        float price = 0;
        if (user.getUserId() != null) {
            cart.setUser(user);
            for (int i = 0; i < cart.getCartItemList().size(); i++) {
                CartItem cartItem = cart.getCartItemList().get(i);
                Medicine medicine = cartItem.getMedicine();
                if (medicine != null) {
                    Medicine medicineDb = medicineRepository.findById(medicine.getMedicineId()).get();
                    price = price + (medicineDb.getPrice() * cartItem.getQuantity());
                    cart.setTotalPrice(price);
                    price = calculateDiscount(price, cart);
                    cart.setDiscountPrice(price);
                }
            }
        cartRepository.save(cart);
        }
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
            if (price > 100 && price < 1000 && discount.getDiscount() == 5) {
                cart.setDiscount(discount);
                cart.setDiscountPercentage(discount.getDiscount());
                float discountPrice = (price * discount.getDiscount()) / 100;
                afterDiscount = price - discountPrice;
                break;
            } else if (price > 1000 && price < 2000 && discount.getDiscount() == 10) {
                cart.setDiscountPercentage(discount.getDiscount());
                float discountPrice = (price * discount.getDiscount()) / 100;
                afterDiscount = price - discountPrice;
                break;
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
    public Cart getById(Long userId) {
        User user = userRepository.findById(userId).get();
        List<Cart> cartList = cartRepository.findAll();
        for (Cart cart : cartList) {
            if (user.getUserId() == cart.getUser().getUserId()) {
                return cart;
            }
        }
        return null;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public List<Cart> getAllCart() {
        return cartRepository.findAll();
    }
}
