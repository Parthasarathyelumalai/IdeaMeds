package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.exception.PrescriptionNotFoundException;
import com.ideas2it.ideameds.exception.UserException;
import com.ideas2it.ideameds.model.Prescription;
import com.ideas2it.ideameds.model.User;
import com.ideas2it.ideameds.service.PrescriptionService;
import com.ideas2it.ideameds.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    /**
     * Add the prescription to the user
     * @param userId To map prescription with the user
     * @param prescription To store the prescription object
     * @return String
     */
    @PostMapping("/prescription/{userId}")
    public ResponseEntity<String> addPrescription(@PathVariable Long userId, @RequestBody Prescription prescription) throws UserException {
        Optional<User> user = userService.getUser(userId);
        List<Prescription> prescriptions = new ArrayList<>();
        if(user.isPresent()) {
            prescriptions.add(prescription);
            user.get().setPrescription(prescriptions);
            Optional<Prescription> prescriptionSaved = prescriptionService.addPrescription(prescription);
            if (prescriptionSaved.isPresent())
                return ResponseEntity.status(HttpStatus.OK).body("Prescription Added Successfully");
            else return ResponseEntity.status(HttpStatus.OK).body("Prescription Not Added");
        } else throw new UserException("User not found");
    }

    /**
     * Retrieve the prescription using prescription ID
     * @param prescriptionId To get the required prescription
     * @return Prescription
     */
    @GetMapping("/prescription/{prescriptionId}")
    public ResponseEntity<Prescription> getPrescription(@PathVariable Long prescriptionId) throws PrescriptionNotFoundException {
        Optional<Prescription> prescription = prescriptionService.getPrescription(prescriptionId);
        if(prescription.isPresent()) return ResponseEntity.status(HttpStatus.FOUND).body(prescription.get());
        else throw new PrescriptionNotFoundException("Prescription Not Found");
    }

    /**
     * Retrieve all the prescriptions associated with the user
     * @param userId To get the
     * @return List<Prescription>
     */
    @GetMapping("/prescription/user/{userId}")
    public ResponseEntity<List<Prescription>> getPrescriptionByUserId(@PathVariable Long userId) throws PrescriptionNotFoundException, UserException {
        Optional<User> user = userService.getUser(userId);

        if (user.isEmpty()) throw new UserException("User not found");
        else {
            List<Prescription> prescriptions = prescriptionService.getPrescriptionByUser(user.get());
            if (prescriptions.isEmpty())
                throw new PrescriptionNotFoundException("Prescription not Found");
            return ResponseEntity.status(HttpStatus.OK).body(prescriptions);
        }
    }

    /**
     * Delete the prescription of a user
     * @param userId To get the required user
     * @param prescriptionId To get the required prescription of the user
     * @return String
     */
    @DeleteMapping("/prescription/{userId}/{prescriptionId}")
    public ResponseEntity<String> deletePrescriptionById(@PathVariable Long userId, @PathVariable Long prescriptionId) throws UserException, PrescriptionNotFoundException {
        Optional<User> user = userService.getUser(userId);

        if(user.isPresent()) {
            List<Prescription> prescriptions = user.get().getPrescription();

            if (prescriptions.isEmpty()) throw new PrescriptionNotFoundException("Prescription Not Found");
            else {
                for (Prescription prescriptionSaved : prescriptions) {
                    if (prescriptionSaved.getPrescriptionId().equals(prescriptionId)) {
                        prescriptionId = prescriptionService.deletePrescriptionById(prescriptionSaved);
                        if (prescriptionId != 0)
                            return ResponseEntity.status(HttpStatus.OK).body("Prescription Deleted Successfully");
                    }
                }
            }
        } else throw new UserException("User not found");
        return ResponseEntity.status(HttpStatus.OK).body("Prescription Not Deleted");
    }

    /**
     * Add the medicines to the cart based on prescription of the user
     * The prescription ID will be given by the user
     * @param prescriptionId To get the required prescription of the user
     * @param userId To get the required User
     * @return String
     */
    @GetMapping("/addToCart/{userId}/{prescriptionId}")
    public ResponseEntity<String> addPrescriptionToCart(@PathVariable Long prescriptionId, @PathVariable Long userId) throws PrescriptionNotFoundException, UserException {
        Optional<User> user = userService.getUser(userId);
        Optional<Prescription> prescription = prescriptionService.getPrescription(prescriptionId);

        if(user.isPresent()) {
            if (prescription.isPresent()) {
                prescriptionService.addToCart(prescription.get().getPrescriptionItems(), user.get());
                return ResponseEntity.status(HttpStatus.CREATED).body("Medicines Added to Cart");
            } else throw new PrescriptionNotFoundException("Prescription Not Found");
        } else throw new UserException("User not found");
    }
}