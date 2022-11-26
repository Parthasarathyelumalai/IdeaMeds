/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.BrandItemsDTO;
import com.ideas2it.ideameds.dto.CartDTO;
import com.ideas2it.ideameds.dto.CartItemDto;
import com.ideas2it.ideameds.dto.MedicineDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.model.*;
import com.ideas2it.ideameds.repository.*;
import com.ideas2it.ideameds.util.Constants;
import com.ideas2it.ideameds.util.DateTimeValidation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final UserRepository userRepository;

    private final DiscountRepository discountRepository;

    private final BrandItemsRepository brandItemsRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     *{@inheritDoc}
     */
    @Override
    public Optional<CartDTO> addCart(Long userId, CartDTO cartDto) throws CustomException {
        List<CartItem> cartItems = this.convertToCartItem(cartDto.getCartItemDtoList());
        Cart cart = modelMapper.map(cartDto, Cart.class);
        cart.setCartItemList(cartItems);
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Optional<Cart> existedCart = cartRepository.findByUser(user.get());
            if(existedCart.isPresent()) {
                cart.setCartId(existedCart.get().getCartId());
                cart.setUser(user.get());
                cart.setCreatedAt(DateTimeValidation.getDate());
                cart.setModifiedAt(DateTimeValidation.getDate());
                existedCart = Optional.of(cart);
                Cart addedCart = getTotalPriceOfCart(existedCart.get());
                Cart savedCart = cartRepository.save(addedCart);
                CartDTO cartDTO = convertToCartDto(savedCart);
                return Optional.of(cartDTO);
            } else {
                cart.setUser(user.get());
                cart.setCreatedAt(DateTimeValidation.getDate());
                cart.setModifiedAt(DateTimeValidation.getDate());
                Cart addedCart = getTotalPriceOfCart(cart);
                Cart savedCart = cartRepository.save(addedCart);
                CartDTO cartDTO = convertToCartDto(savedCart);
                return Optional.of(cartDTO);
            }
        }
        return Optional.empty();
    }

    /**
     * Convert cart item dto list to cart item entity list to save in repository.
     * @param cartItemDtoList - Convert cart item dto list to cart item entity to save in repository.
     * @return - cart item list.
     * @throws CustomException - Brand item not found exception.
     */
    public List<CartItem> convertToCartItem(List<CartItemDto> cartItemDtoList) throws CustomException {
        List<CartItem> cartItemList = new ArrayList<>();
        for (CartItemDto cartItemDto : cartItemDtoList) {
            Optional<BrandItems> brandItems = brandItemsRepository.findById(cartItemDto.getBrandItemsDTO().getBrandItemsId());
            if (brandItems.isPresent()) {
                CartItem cartItem = modelMapper.map(cartItemDto, CartItem.class);
                cartItem.setBrandItems(brandItems.get());
                cartItem.setMedicine(brandItems.get().getMedicine());
                cartItemList.add(cartItemRepository.save(cartItem));
            } else throw new CustomException(Constants.BRAND_ITEM_NOT_FOUND);
        }
        return cartItemList;
    }

    /**
     * This method is used to get total price of cart(Each and every brand items in the cart).
     * @param cart - Get cartItems to calculate total price of the cart.
     * @return - Cart with total price.
     */
    public Cart getTotalPriceOfCart(Cart cart) {
        List<CartItem> cartItems = cart.getCartItemList();
        float price = 0;
        for (CartItem cartItem : cartItems) {
            price = cartItem.getBrandItems().getPrice() * cartItem.getQuantity();
        }
        cart.setTotalPrice(price);
        price = calculateDiscount(price, cart);
        cart.setDiscountPrice(price);
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
            if ((price > 100 && price < 1000 && discount.getDiscount() == 5) || (price > 1000 && price < 2000 && discount.getDiscount() == 10)){
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
     * Convert brand item to brand item dto.
     * @param brandItems - To convert brand item to brand item dto.
     * @return - Brand item dto.
     */
    public Optional<BrandItemsDTO> convertToBrandItemDto(BrandItems brandItems) {
        if (null != brandItems) return Optional.of(modelMapper.map(brandItems, BrandItemsDTO.class));
        return  Optional.empty();
    }

    /**
     * Convert medicine to medicine dto.
     * @param medicine - To convert medicine entity to medicine dto.
     * @return - medicine dto.
     */
    public MedicineDTO convertToMedicineDto(Medicine medicine) throws CustomException {
        if (null != medicine) return modelMapper.map(medicine, MedicineDTO.class);
        else throw new CustomException(Constants.MEDICINE_NOT_FOUND);
    }

    /**
     * Convert cart entity to cart dto to return to the user after save in repository.
     * @param savedCart - To convert cart to cart dto to return.
     * @return CartDto
     * @throws CustomException - Brand item not found.
     */
    private CartDTO convertToCartDto(Cart savedCart) throws CustomException {
        CartDTO cartDTO = modelMapper.map(savedCart, CartDTO.class);
        List<CartItem> cartItemList = savedCart.getCartItemList();
        cartDTO.setCartItemDtoList(convertToCartItemDtoList(cartItemList));
        return cartDTO;
    }

    /**
     * Convert cart items entity to cart items dto to return.
     * @param cartItemList - To convert cart item list to cart item dto list to return.
     * @return CartItemDto list.
     * @throws CustomException - Brand item not found exception.
     */
    private List<CartItemDto> convertToCartItemDtoList(List<CartItem> cartItemList) throws CustomException {
        List<CartItemDto> cartItemDtoList = new ArrayList<>();

        for (CartItem cartItem : cartItemList) {
            CartItemDto cartItemDto = modelMapper.map(cartItem, CartItemDto.class);
            Optional<BrandItemsDTO> brandItemsDTO = convertToBrandItemDto(cartItem.getBrandItems());
            if (brandItemsDTO.isPresent()) {
                cartItemDto.setBrandItemsDTO(brandItemsDTO.get());
                cartItemDto.setMedicineDTO(convertToMedicineDto(cartItem.getMedicine()));
                cartItemDtoList.add(cartItemDto);
            } else throw new CustomException(Constants.BRAND_ITEM_NOT_FOUND);
        }
        return cartItemDtoList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CartDTO getCartByUserId(Long userId) throws CustomException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Optional<Cart> cart = cartRepository.findByUser(user.get());
            if (cart.isPresent()) {
                CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
                List<CartItemDto> cartItemDtoList = convertToCartItemDtoList(cart.get().getCartItemList());
                cartDTO.setCartItemDtoList(cartItemDtoList);
                return cartDTO;
            }
            else throw new CustomException(Constants.CART_ITEM_NOT_FOUND);
        } else throw new CustomException(Constants.USER_NOT_FOUND);
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public List<CartDTO> getAllCart() throws CustomException {
        List<CartDTO> cartDTOList = new ArrayList<>();
        List<Cart> carts = cartRepository.findAll();
        for (Cart cart : carts) {
            CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
            List<CartItemDto> cartItemDtoList = convertToCartItemDtoList(cart.getCartItemList());
            cartDTO.setCartItemDtoList(cartItemDtoList);
            cartDTOList.add(cartDTO);
        }
        return cartDTOList;
    }

    /**
     * Delete user cart by user id.
     *
     * @param userId - To get user and cart.
     * @return boolean.
     */
    @Override
    public boolean deleteCartByUserId(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Optional<Cart> cart = cartRepository.findByUser(user.get());
            cart.ifPresent(value -> cartRepository.deleteById(value.getCartId()));
            return true;
        }
        return false;
    }
}
