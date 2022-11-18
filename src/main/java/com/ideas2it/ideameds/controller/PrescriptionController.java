package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.dto.PrescriptionDTO;
import com.ideas2it.ideameds.model.Prescription;
import com.ideas2it.ideameds.model.PrescriptionItems;
import com.ideas2it.ideameds.model.User;
import com.ideas2it.ideameds.service.PrescriptionItemsService;
import com.ideas2it.ideameds.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Represents the Prescription Controller
 * Contains the end Points for prescription
 * @author Nithish K
 * @version 1.0
 * @since - 2022-11-17
 */
@RestController
public class PrescriptionController {
    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private PrescriptionItemsService prescriptionItemsService;

    @PostMapping("/prescription")
    public String addPrescription(@RequestBody PrescriptionDTO prescriptionDTO){
        String status;
        Long prescriptionId = prescriptionService.addPrescription(prescriptionDTO);
        if(prescriptionId == 0) status = "Prescription Not Added";
        else status = "Prescription Successfully Added";
        return status;
    }

    @GetMapping("/prescription/{prescriptionId}")
    public PrescriptionDTO getPrescription(@PathVariable Long prescriptionId){
        PrescriptionDTO prescriptionDTO = prescriptionService.getPrescription(prescriptionId);
        return prescriptionDTO;
    }

    @GetMapping("/prescription/user/{userId}")
    public List<PrescriptionDTO> getPrescriptionByUserId(@PathVariable Long userId){
        User user = null;
        List<PrescriptionDTO> prescriptionDTOs = prescriptionService.getPrescriptionByUser(user);
        return prescriptionDTOs;
    }

    @PostMapping("/prescriptionItems/{prescriptionId}")
    public String addMedicineForPrescription(@PathVariable Long prescriptionId, @RequestBody PrescriptionItems prescriptionItems){
        String status;
        PrescriptionDTO prescriptionDTO = prescriptionService.getPrescription(prescriptionId);
        Long prescriptionItemsId = prescriptionItemsService.addPrescriptionItems(prescriptionItems,prescriptionDTO);
        if(prescriptionItemsId == 0) status = "Medicines not added";
        else status = "Medicines added to prescription";
        return status;
    }
}
