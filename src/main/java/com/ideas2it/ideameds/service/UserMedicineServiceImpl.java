/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.BrandItemDTO;
import com.ideas2it.ideameds.dto.CartDTO;
import com.ideas2it.ideameds.dto.CartItemDTO;
import com.ideas2it.ideameds.dto.UserMedicineDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.model.BrandItem;
import com.ideas2it.ideameds.model.User;
import com.ideas2it.ideameds.model.UserMedicine;
import com.ideas2it.ideameds.repository.BrandItemRepository;
import com.ideas2it.ideameds.repository.UserMedicineRepository;
import com.ideas2it.ideameds.repository.UserRepository;
import com.ideas2it.ideameds.util.Constants;
import com.ideas2it.ideameds.util.DateTimeValidation;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class for User Medicine Service
 *
 * @author - Parthasarathy Elumalai
 * @version - 1.0
 * @since - 2022-11-21
 */
@Service
@Slf4j
public class UserMedicineServiceImpl implements UserMedicineService {

    private final UserMedicineRepository userMedicineRepository;

    private final UserRepository userRepository;
    private final BrandItemRepository brandItemRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    private final CartService cartService;

    /**
     * Create instance for the class
     *
     * @param userMedicineRepository create instance for user medicine repository
     * @param brandItemRepository   create instance for brand item repository
     * @param cartService            create instance for cart service
     * @param userRepository         create instance for user repository
     */
    @Autowired
    public UserMedicineServiceImpl(UserMedicineRepository userMedicineRepository, UserRepository userRepository, BrandItemRepository brandItemRepository, CartService cartService) {
        this.userMedicineRepository = userMedicineRepository;
        this.userRepository = userRepository;
        this.brandItemRepository = brandItemRepository;
        this.cartService = cartService;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Long addUserMedicine(Long userId, UserMedicineDTO userMedicine) throws CustomException {
        UserMedicine existingUserMedicine = modelMapper.map(userMedicine, UserMedicine.class);
        Optional<User> user = userRepository.findById(userId);
        List<BrandItem> brandItems = brandItemRepository.findAll();
        if ( user.isPresent() ) {
            saveUserMedicine(existingUserMedicine, user.get());
            return addMedicineToCart(existingUserMedicine, user.get().getUserId(), brandItems);
        }
        throw new CustomException(HttpStatus.NOT_FOUND, Constants.USER_NOT_FOUND);
    }

    /**
     * add user medicine in database
     *
     * @param userMedicine - pass user medicine
     * @param user         - pass user details
     */
    private void saveUserMedicine(UserMedicine userMedicine, User user) {
        userMedicine.setUser(user);
        userMedicine.setCreatedAt(DateTimeValidation.getDate());
        userMedicine.setModifiedAt(DateTimeValidation.getDate());
        userMedicineRepository.save(userMedicine);
    }

    /**
     * add medicine to cart
     *
     * @param savedUserMedicine - pass userMedicine
     * @param userId            - pass user id
     * @param brandItems        - send brandItems
     * @return cartId - return id
     * @throws CustomException - occur when medicine not found
     */
    private Long addMedicineToCart(UserMedicine savedUserMedicine, Long userId, List<BrandItem> brandItems) throws CustomException {
        CartDTO cartDTO = null;
        List<CartItemDTO> cartItemDTOS = new ArrayList<>();

        CartItemDTO cartItemDTO = new CartItemDTO();

        if ( !brandItems.isEmpty() ) {
            for (BrandItem brandItem : brandItems) {
                if ( savedUserMedicine.getMedicineName().equals(brandItem.getBrandItemName()) || savedUserMedicine.getMedicineName().equals(brandItem.getMedicine().getMedicineName()) ) {
                    cartDTO = new CartDTO();
                    cartItemDTO.setBrandItemDTO(modelMapper.map(brandItem, BrandItemDTO.class));
                    cartItemDTO.setQuantity(savedUserMedicine.getQuantity());
                    cartItemDTOS.add(cartItemDTO);
                    cartDTO.setCartItemDTOs(cartItemDTOS);
                    break;
                }
            }
        } else throw new CustomException(HttpStatus.NOT_FOUND, Constants.MEDICINE_NOT_AVAILABLE);
        return addCart(cartDTO, userId);
    }

    /**
     * add cart to user
     *
     * @param cartDTO - pass cart dto
     * @param userId  - pass user userId
     * @return cart Id - return cart id
     * @throws CustomException - occur when medicine not found
     */
    private Long addCart(CartDTO cartDTO, Long userId) throws CustomException {
        Optional<CartDTO> existingCart;
        if ( cartDTO != null ) {
            existingCart = cartService.addCart(userId, cartDTO);
            if ( existingCart.isPresent() ) {
                return existingCart.get().getCartId();
            }
        }
        throw new CustomException(HttpStatus.NOT_FOUND, Constants.MEDICINE_NOT_FOUND);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserMedicineDTO> getPreviousUserMedicine(Long userId) {
        return userMedicineRepository.findByUserId(userId).stream().map(userMedicine -> modelMapper.map(userMedicine, UserMedicineDTO.class)).toList();
    }
}
