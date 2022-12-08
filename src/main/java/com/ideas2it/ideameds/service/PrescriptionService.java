/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.PrescriptionDTO;
import com.ideas2it.ideameds.exception.CustomException;

import java.util.List;

/**
 * Service layer of the prescription module.
 * Performs Create, Read, Update and Delete operations for the Prescription
 *
 * @author Nithish K
 * @version 1.0
 * @since 2022-11-18
 */
public interface PrescriptionService {

    /**
     * Get the prescription object and user ID from the controller
     * It validates the user if the user exists the prescription
     * will be passed to the repository to add to database
     * If the user does not exist it will throw custom exception
     *
     * @param prescriptionDTO To pass prescriptionDTO object {@link PrescriptionDTO}
     * @param userId          The id of the user who is adding the prescription.
     * @return returns the prescriptionDTO object
     * @throws CustomException occurs when user not found
     *                         and occurs when prescription was exceeded by 6 months
     */
    PrescriptionDTO addPrescription(PrescriptionDTO prescriptionDTO, Long userId) throws CustomException;

    /**
     * Get the Prescription from the repository
     * if the prescription exists it will return to the controller
     * if the prescription does not exist it will throw custom exception
     *
     * @param prescriptionId To get the required prescription
     * @return returns the PrescriptionDTO object
     * @throws CustomException occurs when prescription was not found
     */
    PrescriptionDTO getPrescriptionByPrescriptionId(Long prescriptionId) throws CustomException;

    /**
     * Get a list of prescriptionDTOs for a given userId.
     * It validates the user if the user exists
     * a list of prescription will be passed to the controller
     * If the user does not exist it will throw custom exception
     *
     * @param userId The userId of the user whose prescriptions are to be fetched.
     * @return returns the list of prescriptionDTO
     * @throws CustomException occurs when user not found
     *                         and occurs when prescription was not found
     */
    List<PrescriptionDTO> getPrescriptionByUser(Long userId) throws CustomException;

    /**
     * Delete the prescription of the user from the user
     * It validates the user if the user exists the prescription id
     * will be checked with the prescription associated with user
     * If the prescription ID matches, the prescription
     * will be deleted in the database
     * If the prescription ID does not matches, it will throw custom exception
     * If the user does not exist it will throw custom exception
     *
     * @param prescriptionId The id of the prescription to be deleted.
     * @param userId         To get the required user
     * @return returns the deleted prescription's ID
     * @throws CustomException occurs when user not found
     *                         and occurs when prescription was not found
     */
    Long deletePrescriptionById(Long prescriptionId, Long userId) throws CustomException;
}