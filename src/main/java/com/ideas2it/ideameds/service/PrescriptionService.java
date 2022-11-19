package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.PrescriptionDTO;
import com.ideas2it.ideameds.model.Prescription;
import com.ideas2it.ideameds.model.PrescriptionItems;
import com.ideas2it.ideameds.model.User;

import java.util.List;

public interface PrescriptionService {
    Long addPrescription(Prescription prescription);
    PrescriptionDTO getPrescription(Long prescriptionId);
    List<PrescriptionDTO> getPrescriptionByUser(User user);

    void addToCart(List<PrescriptionItems> prescriptionItems);
}
