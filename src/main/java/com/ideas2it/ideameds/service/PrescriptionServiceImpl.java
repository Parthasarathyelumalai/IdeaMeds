/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.BrandItemDTO;
import com.ideas2it.ideameds.dto.CartDTO;
import com.ideas2it.ideameds.dto.CartItemDTO;
import com.ideas2it.ideameds.dto.PrescriptionDTO;
import com.ideas2it.ideameds.dto.PrescriptionItemDTO;
import com.ideas2it.ideameds.dto.UserDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.model.Prescription;
import com.ideas2it.ideameds.model.PrescriptionItem;
import com.ideas2it.ideameds.model.User;
import com.ideas2it.ideameds.repository.BrandItemRepository;
import com.ideas2it.ideameds.repository.PrescriptionRepository;
import com.ideas2it.ideameds.repository.UserRepository;
import com.ideas2it.ideameds.util.Constants;
import com.ideas2it.ideameds.util.DateTimeValidation;

import java.util.ArrayList;
import java.util.Collections;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface Implementation
 * Performs Create, Read, Update and Delete operations for the Prescription
 *
 * @author Nithish K
 * @version 1.0
 * @since 2022-11-18
 */
@Service
public class PrescriptionServiceImpl implements PrescriptionService {
    private final PrescriptionRepository prescriptionRepository;
    private final BrandItemRepository brandItemRepository;
    private final UserRepository userRepository;
    private final CartService cartService;
    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Constructs a new object
     *
     * @param prescriptionRepository create new instance for prescription repository
     * @param userRepository         create new instance for user repository
     * @param brandItemRepository   create instance for brand items service
     * @param cartService            create instance for cart service
     */
    @Autowired
    public PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository, UserRepository userRepository,
                                   CartService cartService, BrandItemRepository brandItemRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.brandItemRepository = brandItemRepository;
        this.userRepository = userRepository;
        this.cartService = cartService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PrescriptionDTO addPrescription(PrescriptionDTO prescriptionDTO, Long userId) throws CustomException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Prescription prescription = modelMapper.map(prescriptionDTO, Prescription.class);
            prescription.setPrescriptionItems(prescriptionDTO.getPrescriptionItemDTOs().stream().map(prescriptionItemDTO -> modelMapper.map(prescriptionItemDTO, PrescriptionItem.class)).toList());
            prescription.setUser(user.get());
            DateTimeValidation.validateDateOfIssue(prescriptionDTO.getDateOfIssue());
            prescription.setCreatedAt(DateTimeValidation.getDate());
            prescription.setModifiedAt(DateTimeValidation.getDate());
            return modelMapper.map(prescriptionRepository.save(prescription), PrescriptionDTO.class);
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, Constants.USER_NOT_FOUND);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PrescriptionDTO getPrescriptionByPrescriptionId(Long prescriptionId) throws CustomException {
        Optional<Prescription> prescription = prescriptionRepository.findById(prescriptionId);
        if (prescription.isPresent()) {
            return modelMapper.map(prescription, PrescriptionDTO.class);
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, Constants.PRESCRIPTION_NOT_FOUND);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PrescriptionDTO> getPrescriptionByUser(Long userId) throws CustomException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Optional<List<Prescription>> prescriptions = prescriptionRepository.findByUser(user.get());
            if (prescriptions.isPresent()) {
                return prescriptions.get().stream()
                        .map(prescription -> modelMapper.map(prescription, PrescriptionDTO.class))
                        .toList();
            }
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, Constants.USER_NOT_FOUND);
        }
        return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long deletePrescriptionById(Long prescriptionId, Long userId) throws CustomException {
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            List<Prescription> prescriptions = user.get().getPrescriptions();

            if (prescriptions.isEmpty()) {
                throw new CustomException(HttpStatus.NOT_FOUND, Constants.PRESCRIPTION_NOT_FOUND);
            } else {
                for (Prescription prescription : prescriptions) {
                    if (prescription.getPrescriptionId().equals(prescriptionId)) {
                        prescription.setDeleted(true);
                        return prescriptionRepository.save(prescription).getPrescriptionId();
                    }
                }
            }
        } else throw new CustomException(HttpStatus.NOT_FOUND, Constants.USER_NOT_FOUND);
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public String addPrescriptionToCart(Long prescriptionId,
                                        Long userId) throws CustomException {
        UserDTO userDTO = modelMapper.map(userRepository.findById(userId),UserDTO.class);
        if (null != userDTO) {
        PrescriptionDTO prescriptionDTO = getPrescriptionByPrescriptionId(prescriptionId);
            if (null != prescriptionDTO) {
                DateTimeValidation.validateDateOfIssue(prescriptionDTO.getDateOfIssue());
                if (null != prescriptionDTO.getPrescriptionItemDTOs()) {
                    getMedicinesForCart(prescriptionDTO.getPrescriptionItemDTOs(), userDTO);
                } else throw new CustomException(HttpStatus.NOT_FOUND,Constants.NO_MEDICINE_IN_THE_PRESCRIPTION);
                return Constants.ADDED_TO_CART;
            } else throw new CustomException(HttpStatus.NOT_FOUND, Constants.PRESCRIPTION_NOT_FOUND);
        } else throw new CustomException(HttpStatus.NOT_FOUND, Constants.USER_NOT_FOUND);
    }

    /**
     * Takes a list of prescription items, and a user, and adds the prescription items to the user's cart
     * Get the medicines from the database and check with the prescribed medicines
     * whether it is available or not
     *
     * @param prescriptionItemDTOs    To map the prescribed medicines
     * @param userDTO              To add the medicines to required user's cart
     * @throws CustomException     prescribed medicine is not available in the database
     */
    private void getMedicinesForCart(List<PrescriptionItemDTO> prescriptionItemDTOs, UserDTO userDTO)
            throws CustomException {
        CartDTO cart = new CartDTO();
        List<CartItemDTO> cartItems = new ArrayList<>();
        List<PrescriptionItemDTO> prescriptionItemDTOS = new ArrayList<>();

        for (PrescriptionItemDTO prescriptionItemDTO : prescriptionItemDTOs) {
            BrandItemDTO brandItem = modelMapper.map(brandItemRepository.findBrandItemByBrandItemName(prescriptionItemDTO.getBrandItemName()),BrandItemDTO.class);
            if (brandItem != null) {
                CartItemDTO cartItem = new CartItemDTO();
                cartItem.setBrandItemDTO(brandItem);
                cartItem.setQuantity(prescriptionItemDTO.getQuantity());
                cartItems.add(cartItem);
                cart.setCartItemDTOs(cartItems);
            } else {
                prescriptionItemDTOS.add(prescriptionItemDTO);
            }
        }
        addToCart(prescriptionItemDTOS, userDTO, cart);
    }

    /**
     * Add the prescribed medicines to the cart
     * whether it is available or not
     *
     * @param prescriptionItemDTOS To map the prescribed medicines
     * @param userDTO               To add the medicines to required user's cart
     * @param cartDTO               TO add the medicines in the cart
     * @throws CustomException      prescribed medicine is not available in the database
     */
    private void addToCart(List<PrescriptionItemDTO> prescriptionItemDTOS, UserDTO userDTO, CartDTO cartDTO)
            throws CustomException {
        if (prescriptionItemDTOS.isEmpty())
            cartService.addCart(userDTO.getUserId(), cartDTO);
        else throw new CustomException(HttpStatus.NOT_FOUND, Constants.MEDICINE_NOT_AVAILABLE + prescriptionItemDTOS);
    }
}