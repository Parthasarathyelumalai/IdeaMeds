package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.model.*;
import com.ideas2it.ideameds.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final UserRepository userRepository;

    private final MedicineRepository medicineRepository;

    private final DiscountRepository discountRepository;

    private final BrandItemsRepository brandItemsRepository;

    /**
     *{@inheritDoc}
     */
    @Override
    public Optional<Cart> addCart(Long userId, Cart cart) {
 /*       Optional<User> user = userRepository.findById(userId);
        float price = 0;

        if (user.isPresent()) {
            cart.setUser(user.get());
            List<CartItem> cartItems = new ArrayList<>();
            CartItem cartItem = new CartItem();
            List<CartItem> clientCartItems = cart.getCartItemList();
            for(CartItem cartItemTemp : clientCartItems) {
                Optional<BrandItems> brandItems = brandItemsRepository.findById(cartItemTemp.getBrandItems().getBrandItemsId());
                if (brandItems.isPresent()) {
                    cartItem.setQuantity(cartItemTemp.getQuantity());
                    cartItem.setBrandItems(brandItems.get());
                    cartItem.setMedicine(brandItems.get().getMedicine());
                    cartItems.add(cartItem);
                    price = price + (brandItems.get().getPrice() * cartItem.getQuantity());
                }
            }
            cart.setCartItemList(cartItems);
            cart.setTotalPrice(price);
            price = calculateDiscount(price, cart);
            cart.setDiscountPrice(price);
            cartRepository.save(cart);
        }*/
        return null/*Optional.ofNullable(cart)*/;
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
                cart.setDiscount(discount);
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
