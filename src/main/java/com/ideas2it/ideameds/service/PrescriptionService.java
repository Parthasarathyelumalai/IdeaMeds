/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.PrescriptionDTO;
import com.ideas2it.ideameds.exception.CustomException;

import java.util.List;

/**
 * Service Interface
 * Performs Create, Read, Update and Delete operations for the Prescription
 * @author Nithish K
 * @version 1.0
 * @since 2022-11-18
 */
public interface PrescriptionService {

    /**
     * Get the prescription object from the controller and
     * passes to the repository to add the prescription to the user
     * @param prescriptionDTO To pass prescriptionDTO object
     * @return returns the prescriptionDTO object
     * @throws CustomException occurs when user not found
     * and occurs when prescription was exceeded by 6 months
     */
    PrescriptionDTO addPrescription(PrescriptionDTO prescriptionDTO, Long userId) throws CustomException;

    /**
     * Get the Prescription from the repository
     * @param prescriptionId To get the required prescription
     * @return returns the PrescriptionDTO object
     * @throws CustomException occurs when prescription was not found
     */
    PrescriptionDTO getPrescription(Long prescriptionId) throws CustomException;

    /**
     * Get all the Prescriptions from the repository of a user
     * @param userId To get all the prescriptions of the required user
     * @return returns the list of prescriptionDTO
     * @throws CustomException occurs when user not found
     * and occurs when prescription was not found
     */
    List<PrescriptionDTO> getPrescriptionByUser(Long userId) throws CustomException;

    /**
     * Delete the prescription of the user from the user
     * @param prescriptionId To map the prescription
     * @param userId TO get the required user
     * @return returns the deleted prescription's ID
     * @throws CustomException occurs when user not found
     * and occurs when prescription was not found
     */
    Long deletePrescriptionById(Long prescriptionId, Long userId) throws CustomException;
}