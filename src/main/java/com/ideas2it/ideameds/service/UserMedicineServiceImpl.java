/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.BrandItemsDTO;
import com.ideas2it.ideameds.dto.CartDTO;
import com.ideas2it.ideameds.dto.CartItemDTO;
import com.ideas2it.ideameds.dto.UserMedicineDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.model.BrandItems;
import com.ideas2it.ideameds.model.User;
import com.ideas2it.ideameds.model.UserMedicine;
import com.ideas2it.ideameds.repository.BrandItemsRepository;
import com.ideas2it.ideameds.repository.UserMedicineRepository;
import com.ideas2it.ideameds.repository.UserRepository;
import com.ideas2it.ideameds.util.Constants;
import com.ideas2it.ideameds.util.DateTimeValidation;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final BrandItemsRepository brandItemsRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    private final CartService cartService;

    /**
     * Create instance for the class
     *
     * @param userMedicineRepository create instance for user medicine repository
     * @param brandItemsRepository   create instance for brand item repository
     * @param cartService            create instance for cart service
     * @param userRepository         create instance for user repository
     */
    @Autowired
    public UserMedicineServiceImpl(UserMedicineRepository userMedicineRepository, UserRepository userRepository, BrandItemsRepository brandItemsRepository, CartService cartService) {
        this.userMedicineRepository = userMedicineRepository;
        this.userRepository = userRepository;
        this.brandItemsRepository = brandItemsRepository;
        this.cartService = cartService;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Long addUserMedicine(Long userId, UserMedicineDTO userMedicine) throws CustomException {
        List<BrandItems> brandItems = brandItemsRepository.findAll();
        UserMedicine savedUserMedicine = modelMapper.map(userMedicine, UserMedicine.class);
        CartDTO cartDTO = null;
        Long cartId;
        Optional<User> user = userRepository.findById(userId);
        if ( user.isPresent() ) {
            savedUserMedicine.setUser(user.get());
            savedUserMedicine.setCreatedAt(DateTimeValidation.getDate());
            savedUserMedicine.setModifiedAt(DateTimeValidation.getDate());
            userMedicineRepository.save(savedUserMedicine);
            List<CartItemDTO> cartItemDTOS = new ArrayList<>();
            CartItemDTO cartItemDTO = new CartItemDTO();
            Optional<CartDTO> savedCart;
            if (!brandItems.isEmpty()) {
                for (BrandItems brandItem : brandItems) {
                    if (userMedicine.getMedicineName().equals(brandItem.getBrandItemName()) || userMedicine.getMedicineName().equals(brandItem.getMedicine().getMedicineName())) {
                        cartDTO = new CartDTO();
                        cartItemDTO.setBrandItemsDTO(modelMapper.map(brandItem, BrandItemsDTO.class));
                        cartItemDTO.setQuantity(userMedicine.getQuantity());
                        cartItemDTOS.add(cartItemDTO);
                        cartDTO.setCartItemDTOList(cartItemDTOS);
                    } else {
                        break;
                    }
                }
            } else throw new CustomException(Constants.MEDICINE_NOT_AVAILABLE);

            if (cartDTO != null) {
                    savedCart = cartService.addCart(userId, cartDTO);
                    if (savedCart.isPresent()) {
                        cartId = savedCart.get().getCartId();
                        return cartId;
                    }
                } else {
                    throw new CustomException(userMedicine.getMedicineName() + Constants.MEDICINE_NOT_FOUND);
                }
            }
        throw new CustomException(Constants.USER_NOT_FOUND);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserMedicineDTO> getPreviousUserMedicine(Long userId) {
        return userMedicineRepository.findByUserId(userId).stream().map(userMedicine -> modelMapper.map(userMedicine, UserMedicineDTO.class)).toList();
    }
}
