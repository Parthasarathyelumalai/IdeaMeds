package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.model.Prescription;
import com.ideas2it.ideameds.model.User;
import com.ideas2it.ideameds.service.PrescriptionService;
import com.ideas2it.ideameds.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    /**
     * Add the prescription to the user
     * @param userId To map prescription with the user
     * @param prescription To store the prescription object
     * @return String
     */
    @PostMapping("/prescription/{userId}")
    public ResponseEntity<String> addPrescription(@PathVariable Long userId, @RequestBody Prescription prescription){
        User user = userService.getUserById(userId).get();
        prescription.setUser(user);
        Long prescriptionId = prescriptionService.addPrescription(prescription);
        if(prescriptionId == 0) return ResponseEntity.status(HttpStatus.OK).body("Prescription Not Added");
        else return ResponseEntity.status(HttpStatus.OK).body("Prescription Added Successfully");
    }

    /**
     * Retrieve the prescription using prescription ID
     * @param prescriptionId To get the required prescription
     * @return Prescription
     */

    public ResponseEntity<Prescription> getPrescription(@PathVariable Long prescriptionId){
        return ResponseEntity.status(HttpStatus.FOUND).body(prescriptionService.getPrescription(prescriptionId));
    }

    /**
     * Add the medicines to the cart based on prescription of the user
     * The prescription ID will be given by the user
     * @param prescriptionId To get the required prescription of the user
     * @param userId To get the required User
     * @return String
     */
    @GetMapping("/addToCart/{userId}/{prescriptionId}")
    public ResponseEntity<String> addPrescriptionToCart(@PathVariable Long prescriptionId, @PathVariable Long userId){
        Prescription prescription = prescriptionService.getPrescription(prescriptionId);
        prescriptionService.addToCart(prescription.getPrescriptionItems(),userService.getUserById(userId).get());
        return ResponseEntity.status(HttpStatus.CREATED).body("Medicines Added to Cart");
    }

    /**
     * Retrieve all the prescriptions associated with the user
     * @param userId To get the
     * @return List<Prescription>
     */
    @GetMapping("/prescription/user/{userId}")
    public ResponseEntity<List<Prescription>> getPrescriptionByUserId(@PathVariable Long userId){
        User user = userService.getUserById(userId).get();
        return ResponseEntity.status(HttpStatus.OK).body(prescriptionService.getPrescriptionByUser(user));
    }

    /**
     * Delete the prescription of a user
     * @param userId To get the required user
     * @param prescriptionId To get the required prescription of the user
     * @return String
     */
    @DeleteMapping("/prescription/{userId}/{prescriptionId}")
    public ResponseEntity<String> deletePrescriptionById(@PathVariable Long userId, @PathVariable Long prescriptionId) {
        Long prescription = prescriptionService.deletePrescriptionById(userId,prescriptionId);
        if(prescription == 0) return ResponseEntity.status(HttpStatus.OK).body("Prescription Not Deleted");
        else return  ResponseEntity.status(HttpStatus.OK).body("Prescription Deleted Successfully");
    }
}