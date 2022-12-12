/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.BrandDTO;
import com.ideas2it.ideameds.dto.BrandItemsDTO;
import com.ideas2it.ideameds.dto.CartDTO;
import com.ideas2it.ideameds.dto.CartItemDTO;
import com.ideas2it.ideameds.dto.MedicineDTO;
import com.ideas2it.ideameds.dto.ResponseUserDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.model.Brand;
import com.ideas2it.ideameds.model.BrandItems;
import com.ideas2it.ideameds.model.Cart;
import com.ideas2it.ideameds.model.CartItem;
import com.ideas2it.ideameds.model.Medicine;
import com.ideas2it.ideameds.model.User;
import com.ideas2it.ideameds.repository.BrandItemsRepository;
import com.ideas2it.ideameds.repository.CartRepository;
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
 * It contains the implementation of the cart service interface.
 *
 * @author - Soundharrajan.S
 * @version - 1.0
 * @since - 2022-11-21
 */
@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final BrandItemsRepository brandItemsRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Create instance for the class
     *
     * @param cartRepository       create instance for cart repository
     * @param userRepository       create instance for user repository
     * @param brandItemsRepository create instance for brand items repository
     */
    @Autowired
    public CartServiceImpl(CartRepository cartRepository, UserRepository userRepository, BrandItemsRepository brandItemsRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
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
     * It converts a CartDTO object to a Cart object
     *
     * @param cartDto The CartDTO object that is passed to the method.
     * @return A Cart object
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
     * It converts a list of CartItemDTO to a list of CartItem
     *
     * @param cartItemDTOList The list of cart items that we want to convert to CartItem objects.
     * @return A list of CartItem objects.
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
     * It takes a cart object, iterates through the cart items, and calculates the total price of the cart
     *
     * @param cart The cart object that is passed to the method.
     * @return The total price of the cart is being returned.
     */
    private Cart getTotalPriceOfCart(Cart cart) {
        List<CartItem> cartItems = cart.getCartItemList();
        float totalPrice = 0;
        for (CartItem cartItem : cartItems) {
            totalPrice = cartItem.getBrandItems().getPrice() * cartItem.getQuantity() + totalPrice;
        }
        cart.setTotalPrice(totalPrice);
        return cart;
    }

    /**
     * It converts a Cart object to a CartDTO object.
     *
     * @param cart The cart object that is to be converted to CartDTO.
     * @return A CartDTO object
     * @throws CustomException - Brand item not found.
     */
    private CartDTO convertToCartDto(Cart cart) throws CustomException {
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        List<CartItem> cartItemList = cart.getCartItemList();
        cartDTO.setResponseUserDTO(modelMapper.map(cart.getUser(), ResponseUserDTO.class));
        cartDTO.setCartItemDTOList(convertToCartItemDtoList(cartItemList));
        return cartDTO;
    }

    /**
     * It converts a list of CartItem objects to a list of CartItemDTO objects
     *
     * @param cartItemList The list of cart items that we want to convert to a list of cart item DTOs.
     * @return A list of CartItemDTO objects.
     * @throws CustomException - Cart item list not found, Brand item not found exception.
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
     * It converts a BrandItems object to a BrandItemsDTO object
     *
     * @param brandItems The object that needs to be converted.
     * @return BrandItemsDTO
     * @throws CustomException Brand item not found, Medicine not found, Brand not found.
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
     * It converts a Medicine object to MedicineDTO object
     *
     * @param medicine The medicine object that needs to be converted to MedicineDTO.
     * @return A MedicineDTO object is being returned.
     * @throws CustomException Medicine not found.
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
    public CartDTO getCartByUserId(Long userId) throws CustomException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Optional<Cart> cart = cartRepository.findByUser(user.get());
            if (cart.isPresent()) {
                return convertToCartDto(cart.get());
            } else throw new CustomException(HttpStatus.NOT_FOUND, Constants.CART_ITEM_NOT_FOUND);
        } else throw new CustomException(HttpStatus.NOT_FOUND, Constants.USER_NOT_FOUND);
    }

    /**
     * {@inheritDoc}
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
