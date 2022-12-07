/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.BrandDTO;
import com.ideas2it.ideameds.dto.BrandItemsDTO;
import com.ideas2it.ideameds.dto.CartDTO;
import com.ideas2it.ideameds.dto.CartItemDTO;
import com.ideas2it.ideameds.dto.DiscountDTO;
import com.ideas2it.ideameds.dto.MedicineDTO;
import com.ideas2it.ideameds.dto.UserDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.model.Brand;
import com.ideas2it.ideameds.model.BrandItems;
import com.ideas2it.ideameds.model.Cart;
import com.ideas2it.ideameds.model.CartItem;
import com.ideas2it.ideameds.model.Discount;
import com.ideas2it.ideameds.model.Medicine;
import com.ideas2it.ideameds.model.User;
import com.ideas2it.ideameds.repository.BrandItemsRepository;
import com.ideas2it.ideameds.repository.CartRepository;
import com.ideas2it.ideameds.repository.DiscountRepository;
import com.ideas2it.ideameds.repository.UserRepository;
import com.ideas2it.ideameds.util.Constants;
import com.ideas2it.ideameds.util.DateTimeValidation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final DiscountRepository discountRepository;
    private final BrandItemsRepository brandItemsRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Create instance for the class
     *
     * @param cartRepository       create instance for cart repository
     * @param userRepository       create instance for user repository
     * @param discountRepository   create instance for discount repository
     * @param brandItemsRepository create instance for brand items repository
     */
    @Autowired
    public CartServiceImpl(CartRepository cartRepository, UserRepository userRepository, DiscountRepository discountRepository, BrandItemsRepository brandItemsRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.discountRepository = discountRepository;
        this.brandItemsRepository = brandItemsRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<CartDTO> addCart(Long userId, CartDTO cartDto) throws CustomException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Cart cart = convertToCart(cartDto);
            cart.setUser(user.get());
            Optional<Cart> existedCart = cartRepository.findByUser(user.get());
            if (existedCart.isPresent()) {
                cart.setCartId(existedCart.get().getCartId());
                existedCart = Optional.of(cart);
                Cart savedCart = cartRepository.save(getTotalPriceOfCart(existedCart.get()));
                return Optional.of(convertToCartDto(savedCart));
            } else {
                Cart savedCart = cartRepository.save(getTotalPriceOfCart(cart));
                return Optional.of(convertToCartDto(savedCart));
            }
        } else throw new CustomException(HttpStatus.NOT_FOUND, Constants.USER_NOT_FOUND);
    }

    /**
     * Convert cartDto into cart.
     *
     * @param cartDto - To convert cart entity to cart dto.
     * @return - cart
     * @throws CustomException - Brand item not found exception.
     */
    private Cart convertToCart(CartDTO cartDto) throws CustomException {
        Cart cart = modelMapper.map(cartDto, Cart.class);
        cart.setCreatedAt(DateTimeValidation.getDate());
        cart.setModifiedAt(DateTimeValidation.getDate());
        List<CartItem> cartItems = convertToCartItem(cartDto.getCartItemDTOList());
        cart.setCartItemList(cartItems);
        return cart;
    }

    /**
     * Convert cart item dto list to cart item entity list.
     *
     * @param cartItemDTOList - Convert cart item dto list to cart item entity list.
     * @return - cart item list.
     * @throws CustomException - Brand item not found exception, medicine not found exception.
     */
    private List<CartItem> convertToCartItem(List<CartItemDTO> cartItemDTOList) throws CustomException {
        List<CartItem> cartItems = new ArrayList<>();
        if (null != cartItemDTOList) {
            for (CartItemDTO cartItemDto : cartItemDTOList) {
                Optional<BrandItems> brandItems = brandItemsRepository.findById(cartItemDto.getBrandItemsDTO().getBrandItemsId());
                if (brandItems.isPresent()) {
                    CartItem cartItem = modelMapper.map(cartItemDto, CartItem.class);
                    cartItem.setBrandItems(brandItems.get());
                    cartItems.add(cartItem);
                } else throw new CustomException(HttpStatus.NOT_FOUND, Constants.BRAND_ITEM_NOT_FOUND);
            }
        } else throw new CustomException(HttpStatus.NOT_FOUND, Constants.CART_ITEM_NOT_FOUND);
        return cartItems;
    }

    /**
     * This method is used to get total price of cart(Total price of all brand items).
     *
     * @param cart - Get cartItems to calculate total price of the cart.
     * @return - Cart with total price.
     */
    private Cart getTotalPriceOfCart(Cart cart) {
        List<CartItem> cartItems = cart.getCartItemList();
        float totalPrice = 0;
        for (CartItem cartItem : cartItems) {
            totalPrice = cartItem.getBrandItems().getPrice() * cartItem.getQuantity() + totalPrice;
        }
        cart.setTotalPrice(totalPrice);
        float discountPrice = calculateDiscount(totalPrice, cart);
        cart.setDiscountPrice(discountPrice);
        return cart;
    }

    /**
     * Calculate discount by total price of the medicines(cart items).
     * Set discount related details in cart - discount, discount percentage, discount price.
     *
     * @param totalPrice - To calculate suitable discount.
     * @param cart  - To set discount details in cart.
     * @return price - after calculate discount.
     */
    private float calculateDiscount(float totalPrice, Cart cart) {
        List<Discount> discountList = discountRepository.findAll();
        float afterDiscount = 0;

        for (Discount discount : discountList) {
            if ((totalPrice > 100 && totalPrice < 10000 && discount.getDiscountPercentage() == 5) || (totalPrice > 10000 && totalPrice < 100000 && discount.getDiscountPercentage() == 10)){
                cart.setDiscount(discount);
                cart.setDiscountPercentage(discount.getDiscountPercentage());
                float discountPrice = (totalPrice * discount.getDiscountPercentage()) / 100;
                afterDiscount = totalPrice - discountPrice;
            } else {
                afterDiscount = totalPrice;
            }
        }
        return afterDiscount;
    }

    /**
     * Convert cart entity to cart dto to return to the user after save in repository.
     *
     * @param savedCart - To convert cart to cart dto to return.
     * @return CartDto
     * @throws CustomException - Brand item not found.
     */
    private CartDTO convertToCartDto(Cart savedCart) throws CustomException {
        CartDTO cartDTO = modelMapper.map(savedCart, CartDTO.class);
        List<CartItem> cartItemList = savedCart.getCartItemList();
        cartDTO.setUserDTO(modelMapper.map(savedCart.getUser(), UserDTO.class));
        cartDTO.setDiscountDTO(convertToDiscountDTO(savedCart.getDiscount()));
        cartDTO.setCartItemDTOList(convertToCartItemDtoList(cartItemList));
        return cartDTO;
    }

    /**
     * Convert discount entity to discount dto.
     *
     * @param discount - To convert discount entity to discount dto.
     * @return discount dto.
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
     * Convert cart items entity to cart items dto to return.
     *
     * @param cartItemList - To convert cart item list to cart item dto list to return.
     * @return CartItemDto list.
     * @throws CustomException - Brand item not found exception.
     */
    private List<CartItemDTO> convertToCartItemDtoList(List<CartItem> cartItemList) throws CustomException {
        List<CartItemDTO> cartItemDTOList = new ArrayList<>();
        if (!cartItemList.isEmpty()) {
            for (CartItem cartItem : cartItemList) {
                BrandItemsDTO brandItemsDTO = convertToBrandItemDto(cartItem.getBrandItems());
                CartItemDTO cartItemDto = modelMapper.map(cartItem, CartItemDTO.class);
                cartItemDto.setBrandItemsDTO(brandItemsDTO);
                cartItemDTOList.add(cartItemDto);
            }
        } else throw new CustomException(HttpStatus.NOT_FOUND, Constants.CART_ITEM_NOT_FOUND);
        return cartItemDTOList;
    }

    /**
     * Brand item entity convert into brand item dto.
     *
     * @param brandItems - To convert brand item entity to brand item dto.
     * @return - Brand item dto.
     */
    private BrandItemsDTO convertToBrandItemDto(BrandItems brandItems) throws CustomException {
        BrandItemsDTO brandItemsDTO = modelMapper.map(brandItems, BrandItemsDTO.class);
        if (null != brandItemsDTO) {
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
     * Convert medicine to medicine dto.
     *
     * @param medicine - To convert medicine entity to medicine dto.
     * @return - medicine dto.
     */
    private MedicineDTO convertToMedicineDto(Medicine medicine) throws CustomException {
        if (null != medicine) {
            return modelMapper.map(medicine, MedicineDTO.class);
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, Constants.MEDICINE_NOT_FOUND);
        }
    }

    /**
     * Convert brand entity to brand dto.
     * @param brand - To convert brand entity to brand dto.
     * @return Brand dto
     * @throws CustomException - brand not found.
     */
    private BrandDTO convertToBrandDto(Brand brand) throws CustomException {
        if (null != brand) {
            return modelMapper.map(brand, BrandDTO.class);
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, Constants.BRAND_NOT_FOUND);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<CartDTO> getCartByUserId(Long userId) throws CustomException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Optional<Cart> cart = cartRepository.findByUser(user.get());
            if (cart.isPresent()) {
                CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
                List<CartItemDTO> cartItemDTOList = convertToCartItemDtoList(cart.get().getCartItemList());
                cartDTO.setCartItemDTOList(cartItemDTOList);
                return Optional.of(cartDTO);
            } else throw new CustomException(HttpStatus.NOT_FOUND, Constants.CART_ITEM_NOT_FOUND);
        } else throw new CustomException(HttpStatus.NOT_FOUND, Constants.USER_NOT_FOUND);
    }

    /**
     * Delete user cart by user id.
     *
     * @param userId - To get user and cart.
     * @return boolean.
     * @throws CustomException - Cart item not found, user not found.
     */
    @Override
    public boolean deleteCartByUserId(Long userId) throws CustomException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent() && !user.get().isDeletedStatus()) {
            Optional<Cart> cart = cartRepository.findByUser(user.get());
            if (cart.isPresent()) {
                cart.get().setUser(null);
                cartRepository.deleteById(cart.get().getCartId());
                return true;
            } else throw new CustomException(HttpStatus.NOT_FOUND, Constants.CART_ITEM_NOT_FOUND);
        } else throw new CustomException(HttpStatus.NOT_FOUND, Constants.USER_NOT_FOUND);
    }
}
