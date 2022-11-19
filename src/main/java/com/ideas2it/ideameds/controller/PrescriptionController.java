package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.dto.PrescriptionDTO;
import com.ideas2it.ideameds.model.Prescription;
import com.ideas2it.ideameds.model.User;
import com.ideas2it.ideameds.service.PrescriptionService;
import com.ideas2it.ideameds.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/prescription/{userId}")
    public String addPrescription(@PathVariable Long userId, @RequestBody Prescription prescription){
        String status;
        User user = userService.getUser(userId).get();
        prescription.setUser(user);
        Long prescriptionId = prescriptionService.addPrescription(prescription);
        if(prescriptionId == 0) status = "Prescription Not Added";
        else status = "Prescription Successfully Added";
        return status;
    }

    @GetMapping("/prescription/{prescriptionId}")
    public PrescriptionDTO getPrescription(@PathVariable Long prescriptionId){
        return prescriptionService.getPrescription(prescriptionId);
    }

    @GetMapping("/addtocart/{prescriptionId}")
    public String addPrescriptionToCart(@PathVariable Long prescriptionId){
        String status = null;
/*        List<PrescriptionItems> prescriptionItems = prescriptionService.getPrescription(prescriptionId).getPrescriptionItems();
        if(prescriptionItems != null)
            prescriptionService.addToCart(prescriptionItems);*/
        return status;
    }

    @GetMapping("/prescription/user/{userId}")
    public List<PrescriptionDTO> getPrescriptionByUserId(@PathVariable Long userId){
        User user = userService.getUser(userId).get();
        List<PrescriptionDTO> prescriptionDTOs = prescriptionService.getPrescriptionByUser(user);
        return prescriptionDTOs;
    }
}