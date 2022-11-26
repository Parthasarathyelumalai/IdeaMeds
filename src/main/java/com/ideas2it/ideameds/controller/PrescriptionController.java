/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.dto.BrandItemsDTO;
import com.ideas2it.ideameds.dto.PrescriptionDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.model.BrandItems;
import com.ideas2it.ideameds.model.Cart;
import com.ideas2it.ideameds.model.CartItem;
import com.ideas2it.ideameds.model.PrescriptionItems;
import com.ideas2it.ideameds.model.User;
import com.ideas2it.ideameds.service.BrandItemsService;
import com.ideas2it.ideameds.service.CartServiceImpl;
import com.ideas2it.ideameds.service.PrescriptionService;
import com.ideas2it.ideameds.service.UserService;
import com.ideas2it.ideameds.util.Constants;
import com.ideas2it.ideameds.util.DateTimeValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represents the Prescription Controller
 * Contains the end Points for prescription
 * @author Nithish K
 * @version 1.0
 * @since - 2022-11-19
 */
@RestController
@RequiredArgsConstructor
public class PrescriptionController {
    private final PrescriptionService prescriptionService;
    private final UserService userService;
    private final DateTimeValidation dateTimeValidation;
    private final BrandItemsService brandItemsService;
    private final CartServiceImpl cartService;


    /**
     * Add the prescription to the user
     * @param userId To map prescription with the user
     * @param prescriptionDTO To store the prescriptionDTO object
     * @return returns the httpStatus and message
     * @throws CustomException occurs when user not found
     * amd occurs when prescription was exceeded by 6 months
     */
    @PostMapping( "/prescription/{userId}")
    public ResponseEntity<String> addPrescription(@Valid @RequestBody PrescriptionDTO prescriptionDTO, @PathVariable Long userId) throws CustomException {
        PrescriptionDTO prescriptionSaved = prescriptionService.addPrescription(prescriptionDTO, userId);

        if (null != prescriptionSaved) return ResponseEntity.status(HttpStatus.OK).body("Prescription Added Successfully");
            else return ResponseEntity.status(HttpStatus.OK).body("Prescription Not Added");
    }

    /**
     * Retrieve the prescription using prescription ID
     * @param prescriptionId To get the required prescription
     * @return returns the httpStatus and Prescription DTO
     * @throws CustomException occurs when prescription was not found
     */
    @GetMapping("/prescription/{prescriptionId}")
    public ResponseEntity<PrescriptionDTO> getPrescription(@PathVariable Long prescriptionId) throws CustomException {
        return ResponseEntity.status(HttpStatus.FOUND).body(prescriptionService.getPrescription(prescriptionId));
    }

    /**
     * Retrieve all the prescriptions associated with the user
     * @param userId To get the
     * @return returns the httpStatus and list of prescription DTOs
     * @throws CustomException occurs when user not found
     * @throws CustomException occurs when prescription was not found
     */
    @GetMapping("/prescription/user/{userId}")
    public ResponseEntity<List<PrescriptionDTO>> getPrescriptionByUserId(@PathVariable Long userId) throws CustomException {
        List<PrescriptionDTO> prescriptions = prescriptionService.getPrescriptionByUser(userId);
        if (prescriptions.isEmpty())
            throw new CustomException(Constants.PRESCRIPTION_NOT_FOUND);
        return ResponseEntity.status(HttpStatus.OK).body(prescriptions);
    }

    /**
     * Delete the prescription of a user
     * @param userId To get the required user
     * @param prescriptionId To get the required prescription of the user
     * @return returns the httpStatus and a message
     * @throws CustomException occurs when user not found
     * and occurs when prescription was not found
     */
    @DeleteMapping("/prescription/{userId}/{prescriptionId}")
    public ResponseEntity<String> deletePrescriptionById(@PathVariable Long userId, @PathVariable Long prescriptionId) throws CustomException {
        Long prescriptionById = prescriptionService.deletePrescriptionById(prescriptionId, userId);

        if (null != prescriptionById) return ResponseEntity.status(HttpStatus.OK).body("Prescription Deleted Successfully");
        else return ResponseEntity.status(HttpStatus.OK).body("Prescription Not Deleted");
    }

    /**
     * Add the medicines to the cart based on prescription of the user
     * The prescription ID will be given by the user
     * @param prescriptionId To get the required prescription of the user
     * @param userId To get the required User
     * @return returns the http status and a message
     * @throws CustomException occurs when user not found
     * and occurs when prescription was not found
     */
//    @GetMapping("/addToCart/{userId}/{prescriptionId}")
//    public ResponseEntity<String> addPrescriptionToCart(@PathVariable Long prescriptionId, @PathVariable Long userId) throws CustomException {
//        Optional<User> user = Optional.ofNullable(userService.getUserById(userId));
//        PrescriptionDTO prescription = prescriptionService.getPrescription(prescriptionId);
//
//        if(user.isPresent()) {
//            if (null != prescription) {
//                dateTimeValidation.validateDateOfIssue(prescription.getDateOfIssue());
//                addToCart(prescription.getPrescriptionItems(), user.get());
//                return ResponseEntity.status(HttpStatus.CREATED).body("Medicines Added to Cart");
//            } else throw new CustomException(Constants.PRESCRIPTION_NOT_FOUND);
//        } else throw new CustomException(Constants.USER_NOT_FOUND);
//    }

    /**
     * Add the prescribed medicines to the cart
     * @param prescriptionItems To map the prescribed medicines
     * @param user To add the medicines to required user's cart
     */
//    private void addToCart(List<PrescriptionItems> prescriptionItems, User user) {
//        Cart cart = new Cart();
//        List<CartItem> cartItems = new ArrayList<>();
//        if(prescriptionItems != null){
//            List<BrandItemsDTO> brandItemsList = brandItemsService.getAllBrandItems();
//            for(PrescriptionItems prescriptionItem : prescriptionItems) {
//                for (BrandItemsDTO brandItem : brandItemsList) {
//                    if (brandItem.getBrandItemName().equals(prescriptionItem.getBrandItemName())) {
//                        CartItemDTO cartItem = new CartItem();
//                        cartItem.setBrandItems(brandItem);
//                        cartItem.setQuantity(prescriptionItem.getQuantity());
//                        cartItems.add(cartItem);
//                        cart.setCartItemList(cartItems);
//                    }
//                }
//            }
//        }
//        cartService.addCart(user.getUserId(), cart);
//    }
}