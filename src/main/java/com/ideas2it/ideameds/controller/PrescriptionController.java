/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.dto.BrandItemsDTO;
import com.ideas2it.ideameds.dto.CartDTO;
import com.ideas2it.ideameds.dto.CartItemDTO;
import com.ideas2it.ideameds.dto.PrescriptionDTO;
import com.ideas2it.ideameds.dto.PrescriptionItemsDTO;
import com.ideas2it.ideameds.dto.ResponseUserDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.service.BrandItemsService;
import com.ideas2it.ideameds.service.CartService;
import com.ideas2it.ideameds.service.CartServiceImpl;
import com.ideas2it.ideameds.service.PrescriptionService;
import com.ideas2it.ideameds.service.UserService;
import com.ideas2it.ideameds.util.Constants;
import com.ideas2it.ideameds.util.DateTimeValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the Prescription Controller
 * Contains the end Points for prescription
 *
 * @author Nithish K
 * @version 1.0
 * @since - 2022-11-19
 */
@RestController
public class PrescriptionController {
    private final PrescriptionService prescriptionService;
    private final UserService userService;
    private final BrandItemsService brandItemsService;
    private final CartService cartService;

    /**
     * Constructs object for the classes
     *
     * @param prescriptionService create instance for prescription service
     * @param userService        create instance for user service
     * @param brandItemsService   create instance for brand items service
     * @param cartService         create instance for cart service
     */
    @Autowired
    public PrescriptionController(PrescriptionService prescriptionService, UserService userService, BrandItemsService brandItemsService, CartServiceImpl cartService) {
        this.prescriptionService = prescriptionService;
        this.userService = userService;
        this.brandItemsService = brandItemsService;
        this.cartService = cartService;
    }

    /**
     * Add the prescription to the user
     * Get the input for each field in the prescription DTO from the user as DTO object
     * Only the validated object will be passed to the service
     *
     * @param userId          To map prescription with the user
     * @param prescriptionDTO To store the prescriptionDTO object {@link PrescriptionDTO}
     * @return returns the httpStatus and message
     * @throws CustomException occurs when user not found
     *                         and occurs when prescription was exceeded by 6 months
     */
    @PostMapping("/prescription/{userId}")
    public ResponseEntity<PrescriptionDTO> addPrescription(@Valid @RequestBody PrescriptionDTO prescriptionDTO, @PathVariable Long userId) throws CustomException {
        return ResponseEntity.status(HttpStatus.OK).body(prescriptionService.addPrescription(prescriptionDTO, userId));
    }

    /**
     * Retrieve the prescription using prescription ID
     * Get the prescription ID from the path variable
     *
     * @param prescriptionId To get the required prescription
     * @return returns the httpStatus and Prescription DTO
     * @throws CustomException occurs when prescription was not found
     */
    @GetMapping("/prescription/{prescriptionId}")
    public ResponseEntity<PrescriptionDTO> getPrescriptionByPrescriptionId(@PathVariable Long prescriptionId) throws CustomException {
        return ResponseEntity.status(HttpStatus.FOUND).body(prescriptionService.getPrescriptionByPrescriptionId(prescriptionId));
    }


    /**
     * Retrieve all the prescriptions associated with the user
     * returns a list of prescriptions for a user with the given userId
     *
     * @param userId The id of the user whose prescriptions are to be fetched.
     * @return returns the httpStatus and list of prescription DTOs
     * @throws CustomException occurs when user not found
     *                         and occurs when prescription was not found
     */
    @GetMapping("/prescription/user/{userId}")
    public ResponseEntity<List<PrescriptionDTO>> getPrescriptionByUserId(@PathVariable Long userId) throws CustomException {
        List<PrescriptionDTO> prescriptions = prescriptionService.getPrescriptionByUser(userId);
        if (prescriptions.isEmpty())
            throw new CustomException(HttpStatus.NOT_FOUND, Constants.PRESCRIPTION_NOT_FOUND);
        return ResponseEntity.status(HttpStatus.FOUND).body(prescriptions);
    }

    /**
     * Delete the prescription of a user
     * If the prescription deleted it will show successfully message
     * If the prescription not deleted it will show unsuccessfully message
     *
     * @param userId         To get the required user
     * @param prescriptionId To get the required prescription of the user
     * @return returns the httpStatus and a message
     * @throws CustomException occurs when user not found
     *                         and occurs when prescription was not found
     */
    @DeleteMapping("/prescription/{userId}/{prescriptionId}")
    public ResponseEntity<String> deletePrescriptionById(@PathVariable Long userId, @PathVariable Long prescriptionId) throws CustomException {
        Long prescriptionById = prescriptionService.deletePrescriptionById(prescriptionId, userId);

        if (null != prescriptionById)
            return ResponseEntity.status(HttpStatus.OK).body(Constants.DELETED_SUCCESSFULLY);
        else return ResponseEntity.status(HttpStatus.OK).body(Constants.NOT_DELETED_SUCCESSFULLY);
    }

    /**
     * Add the medicines to the cart based on prescription of the user
     * The prescription ID will be given by the user
     *
     * @param prescriptionId The id of the prescription that you want to add to the cart.
     * @param userId         The id of the user who is adding the prescription to the cart.
     * @return returns the http status and a message
     * @throws CustomException occurs when user not found
     *                         and occurs when prescription was not found
     */
    @GetMapping("/add-to-cart/{userId}/{prescriptionId}")
    public ResponseEntity<String> addPrescriptionToCart(@PathVariable Long prescriptionId, @PathVariable Long userId) throws CustomException {
        ResponseUserDTO userDTO = userService.getUserById(userId);
        PrescriptionDTO prescriptionDTO = prescriptionService.getPrescriptionByPrescriptionId(prescriptionId);

        if (null != userDTO) {
            if (null != prescriptionDTO) {
                DateTimeValidation.validateDateOfIssue(prescriptionDTO.getDateOfIssue());
                if (null != prescriptionDTO.getPrescriptionItems()) {
                    getMedicinesForCart(prescriptionDTO.getPrescriptionItems(), userDTO);
                } else
                    return ResponseEntity.status(HttpStatus.CREATED).body(Constants.NO_MEDICINE_IN_THE_PRESCRIPTION);
                return ResponseEntity.status(HttpStatus.CREATED).body(Constants.ADDED_TO_CART);
            } else throw new CustomException(HttpStatus.NOT_FOUND, Constants.PRESCRIPTION_NOT_FOUND);
        } else throw new CustomException(HttpStatus.NOT_FOUND, Constants.USER_NOT_FOUND);
    }

    /**
     * Takes a list of prescription items, and a user, and adds the prescription items to the user's cart
     * Get the medicines from the database and check with the prescribed medicines
     * whether it is available or not
     *
     * @param prescriptionItems To map the prescribed medicines
     * @param user              To add the medicines to required user's cart
     * @throws CustomException occurs when prescription was
     *                         exceeded by 6 months
     */
    private void getMedicinesForCart(List<PrescriptionItemsDTO> prescriptionItems, ResponseUserDTO user) throws CustomException {
        CartDTO cart = new CartDTO();
        List<CartItemDTO> cartItems = new ArrayList<>();
        List<PrescriptionItemsDTO> prescriptionItemsDTOs = new ArrayList<>();

        for (PrescriptionItemsDTO prescriptionItem : prescriptionItems) {
            BrandItemsDTO brandItem = brandItemsService.getBrandItemByName(prescriptionItem.getBrandItemName());
                if (brandItem != null) {
                    CartItemDTO cartItem = new CartItemDTO();
                    cartItem.setBrandItemsDTO(brandItem);
                    cartItem.setQuantity(prescriptionItem.getQuantity());
                    cartItems.add(cartItem);
                    cart.setCartItemDTOList(cartItems);
                } else {
                    prescriptionItemsDTOs.add(prescriptionItem);
                }
        }
        addToCart(prescriptionItemsDTOs, user, cart);
    }

    /**
     * Add the prescribed medicines to the cart
     * whether it is available or not
     *
     * @param prescriptionItemsDTOs To map the prescribed medicines
     * @param userDTO               To add the medicines to required user's cart
     * @param cartDTO               TO add the medicines in the cart
     * @throws CustomException occurs when prescription was
     *                         exceeded by 6 months and when prescribed medicine is not available in the database
     */
    private void addToCart(List<PrescriptionItemsDTO> prescriptionItemsDTOs, ResponseUserDTO userDTO, CartDTO cartDTO) throws CustomException {
        if (prescriptionItemsDTOs.isEmpty())
            cartService.addCart(userDTO.getUserId(), cartDTO);
        else throw new CustomException(HttpStatus.NOT_FOUND, Constants.MEDICINE_NOT_AVAILABLE + prescriptionItemsDTOs);
    }
}