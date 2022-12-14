/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.dto.PrescriptionDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.service.PrescriptionService;
import com.ideas2it.ideameds.util.Constants;
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

    /**
     * Constructs object for the classes
     *
     * @param prescriptionService create instance for prescription service
     */
    @Autowired
    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
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
    public ResponseEntity<PrescriptionDTO> addPrescription(@Valid @RequestBody PrescriptionDTO prescriptionDTO,
                                                           @PathVariable Long userId) throws CustomException {
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
    public ResponseEntity<PrescriptionDTO> getPrescriptionByPrescriptionId(@PathVariable Long prescriptionId)
            throws CustomException {
        return ResponseEntity.status(HttpStatus.OK).body(prescriptionService.getPrescriptionByPrescriptionId(prescriptionId));
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
        List<PrescriptionDTO> prescriptionDTOs = prescriptionService.getPrescriptionByUser(userId);
        if (prescriptionDTOs.isEmpty())
            throw new CustomException(HttpStatus.NOT_FOUND, Constants.PRESCRIPTION_NOT_FOUND);
        return ResponseEntity.status(HttpStatus.OK).body(prescriptionDTOs);
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
    public ResponseEntity<String> deletePrescriptionById(@PathVariable Long userId,
                                                         @PathVariable Long prescriptionId) throws CustomException {
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
    public ResponseEntity<String> addPrescriptionToCart(@PathVariable Long prescriptionId,
                                                        @PathVariable Long userId) throws CustomException {
        String status = prescriptionService.addPrescriptionToCart(prescriptionId,userId);
        return ResponseEntity.status(HttpStatus.OK).body(status);
    }
}