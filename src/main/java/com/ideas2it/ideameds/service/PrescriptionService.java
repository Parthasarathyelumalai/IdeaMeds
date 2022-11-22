package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.exception.PrescriptionNotFoundException;
import com.ideas2it.ideameds.exception.UserException;
import com.ideas2it.ideameds.model.Prescription;
import com.ideas2it.ideameds.model.PrescriptionItems;
import com.ideas2it.ideameds.model.User;

import java.util.List;
import java.util.Optional;

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
     * @param prescription To save prescription object
     * @return Long returns the prescription ID will be
     */
    Optional<Prescription> addPrescription(Prescription prescription);

    /**
     * Get the Prescription from the repository
     *
     * @param prescriptionId To get the required prescription
     * @return Prescription - returns the Prescription object
     */
    Optional<Prescription> getPrescription(Long prescriptionId) throws PrescriptionNotFoundException;

    /**
     * Get all the Prescriptions from the repository of a user
     * @param user To get prescription of required user
     * @return List - returns the list of prescription
     */
    List<Prescription> getPrescriptionByUser(User user) throws PrescriptionNotFoundException;

    /**
     * Add the prescribed medicines to the cart
     * @param prescriptionItems To map the prescribed medicines
     * @param user To add the medicines to required user's cart
     */
    void addToCart(List<PrescriptionItems> prescriptionItems, User user);

    /**
     * Delete the prescription of the user from the user
     * @param prescription To map the prescription
     * @return Long - returns the deleted prescription's ID
     */
    Long deletePrescriptionById(Prescription prescription) throws UserException, PrescriptionNotFoundException;
}