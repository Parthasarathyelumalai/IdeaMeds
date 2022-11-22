package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.model.Cart;
import com.ideas2it.ideameds.model.CartItem;
import com.ideas2it.ideameds.model.Discount;
import com.ideas2it.ideameds.model.Medicine;
import com.ideas2it.ideameds.model.User;
import com.ideas2it.ideameds.repository.CartRepository;
import com.ideas2it.ideameds.repository.DiscountRepository;
import com.ideas2it.ideameds.repository.MedicineRepository;
import com.ideas2it.ideameds.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


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
    public Optional<Cart> addCart(Long userId, Cart cart) {
        Optional<User> user = userRepository.findById(userId);
        float price = 0;
        if (user.isPresent()) {
            cart.setUser(user.get());
            for (int i = 0; i < cart.getCartItemList().size(); i++) {
                CartItem cartItem = cart.getCartItemList().get(i);
                Medicine medicine = cartItem.getMedicine();
                Optional<Medicine> medicineDb = medicineRepository.findById(medicine.getMedicineId());
                if (medicineDb.isPresent()) {
                    price = price + (medicineDb.get().getPrice() * cartItem.getQuantity());
                    cart.setTotalPrice(price);
                    price = calculateDiscount(price, cart);
                    cart.setDiscountPrice(price);
                }
            }
        cartRepository.save(cart);
        }
        return Optional.ofNullable(cart);
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
            } else if (price > 1000 && price < 2000 && discount.getDiscount() == 10) {
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
     *{@inheritDoc}
     */
    @Override
    public Optional<Cart> getById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        List<Cart> cartList = cartRepository.findAll();
        if (user.isPresent()) {
            for (Cart cart : cartList) {
                if (Objects.equals(user.get().getUserId(), cart.getUser().getUserId())) {
                    return Optional.of(cart);
                }
            }
        }
        return Optional.empty();
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public List<Cart> getAllCart() {
        return cartRepository.findAll();
    }
}
