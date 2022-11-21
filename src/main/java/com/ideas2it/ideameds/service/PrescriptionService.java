package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.model.Prescription;
import com.ideas2it.ideameds.model.PrescriptionItems;
import com.ideas2it.ideameds.model.User;

import java.util.List;

/**
 * Service Interface
 * Performs Create, Read, Update and Delete operations for the Prescription
 * @author Nithish K
 * @version 1.0
 * @since 2022-11-18
 */
public interface PrescriptionService {
    Long addPrescription(Prescription prescription);
    Prescription getPrescription(Long prescriptionId);
    List<Prescription> getPrescriptionByUser(User user);
    void addToCart(List<PrescriptionItems> prescriptionItems, User user);
    Long deletePrescriptionById(Long userId, Long prescriptionId);
}