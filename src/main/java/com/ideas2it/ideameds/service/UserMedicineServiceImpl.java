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
import com.ideas2it.ideameds.model.UserMedicine;
import com.ideas2it.ideameds.repository.BrandItemsRepository;
import com.ideas2it.ideameds.repository.UserMedicineRepository;
import com.ideas2it.ideameds.util.Constants;
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
 * @since - 2022-11-21
 * @version - 1.0
 */
@Service
@Slf4j
public class UserMedicineServiceImpl implements UserMedicineService {

    private final UserMedicineRepository userMedicineRepository;

    private final BrandItemsRepository brandItemsRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    private final CartService cartService;

    /**
     * Create instance for the class
     * @param userMedicineRepository create instance for user medicine repository
     * @param brandItemsRepository create instance for brand item repository
     * @param cartService create instance for cart service
     */
    @Autowired
    public UserMedicineServiceImpl(UserMedicineRepository userMedicineRepository, BrandItemsRepository brandItemsRepository, CartService cartService) {
        this.userMedicineRepository = userMedicineRepository;
        this.brandItemsRepository = brandItemsRepository;
        this.cartService = cartService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long addUserMedicine(Long userId, UserMedicineDTO userMedicine) throws CustomException {
        List<BrandItemsDTO> brandItems = brandItemsRepository.findAll().stream().map(brandItem -> modelMapper.map(brandItem, BrandItemsDTO.class)).toList();
        userMedicineRepository.save(modelMapper.map(userMedicine,UserMedicine.class));
        List<CartItemDTO> cartItemDTOS = new ArrayList<>();
        CartItemDTO cartItemDTO = new CartItemDTO();
        Optional<CartDTO> savedCart;
        CartDTO cartDTO = null;
        Long cartId = null;
        for (BrandItemsDTO brandItem : brandItems) {
            if ( userMedicine.getMedicineName().equals(brandItem.getMedicineDTO().getMedicineName()) || userMedicine.getMedicineName().equals(brandItem.getBrandItemName())) {
                cartDTO = new CartDTO();
                cartItemDTO.setBrandItemsDTO(brandItem);
                cartItemDTO.setQuantity(userMedicine.getQuantity());
                cartItemDTOS.add(cartItemDTO);
                cartDTO.setCartItemDTOList(cartItemDTOS);
            } else {
                break;
            }
        }
        if (cartDTO != null) {
            savedCart = cartService.addCart(userId, cartDTO);
            if ( savedCart.isPresent() ) {
                cartId = savedCart.get().getCartId();
                return cartId;
            }
        } else {
            throw new CustomException(userMedicine.getMedicineName() + Constants.MEDICINE_NOT_FOUND);
        }
        return cartId;
    }
}
