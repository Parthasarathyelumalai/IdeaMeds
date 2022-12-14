/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.dto.MedicineDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.service.MedicineService;
import com.ideas2it.ideameds.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * Controller for Medicine details
 * </p>
 *
 * @author Dinesh Kumar R
 * @version 1.0
 * @since 2022-11-18
 */
@RestController
@RequestMapping("/medicine")
public class MedicineController {
    private final MedicineService medicineService;

    /**
     * <p>
     * Constructs a new object for the corresponding services
     * </p>
     *
     * @param medicineService creates new instance for medicine service
     */
    @Autowired
    public MedicineController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }


    /**
     * Adds the new medicine record to the database.
     * Each entry contains validation to ensure the valid medicine.
     * Multiple medicine records cannot have the same name.
     * Medicine contains the formal info about medicine
     * A medicine can be assigned to many brand Items
     *
     * @param medicineDTO new medicine to add
     * @return medicine which was added in the database successfully
     * @throws CustomException throws when the new medicine name already exist
     */
    @PostMapping
    public ResponseEntity<MedicineDTO> addMedicine(@Valid @RequestBody MedicineDTO medicineDTO) throws CustomException {
        return ResponseEntity.status(HttpStatus.CREATED).body(medicineService.addMedicine(medicineDTO));
    }

    /**
     * It returns a list of all medicines in the database
     * @return list of medicines available
     * @throws CustomException throws exception when there is no entry for medicine
     */
    @GetMapping("/get-all")
    public ResponseEntity<List<MedicineDTO>> getAllMedicines() throws CustomException {
        List<MedicineDTO> medicineDTOs = medicineService.getAllMedicines();

        if (medicineDTOs != null) {
            return ResponseEntity.status(HttpStatus.OK).body(medicineDTOs);
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, Constants.MEDICINE_NOT_FOUND);
        }

    }

    /**
     * This function gets medicine using the id
     * A medicine id should be exact same compared with brand id
     * from the database
     *
     * @param medicineId medicine id for getting medicine
     * @return medicine using the id
     * @throws CustomException throws exception when there is no medicine found
     */
    @GetMapping("/{medicineId}")
    public ResponseEntity<MedicineDTO> getMedicineById(@PathVariable("medicineId") Long medicineId) throws CustomException {
        return ResponseEntity.status(HttpStatus.OK).body(medicineService.getMedicineById(medicineId));
    }

    /**
     * gets medicine using name got from the request.
     * A medicine name should be exact same compared with brand
     * name available in the database.
     *
     * @param medicineName medicine name for getting medicine
     * @return medicine using the medicine name
     * @throws CustomException throws exception when there is no medicine found
     */
    @GetMapping("/by-name/{medicineName}")
    public ResponseEntity<MedicineDTO> getMedicineByName(@PathVariable("medicineName") String medicineName) throws CustomException {
        return ResponseEntity.status(HttpStatus.OK).body(medicineService.getMedicineByName(medicineName));
    }

    /**
     * Updates the existing medicine in the database
     * The medicine will be found by the id and gets updated
     * Update process requires a valid medicine, to ensure it, each entry
     * have validation.
     *
     * @param medicineDTO updated medicine DTO to update an existing record
     * @return medicine Dto after it was updated successfully
     * @throws CustomException throws exception when there is no medicine found
     */
    @PutMapping
    public ResponseEntity<MedicineDTO> updateMedicine(@Valid @RequestBody MedicineDTO medicineDTO) throws CustomException {
        return ResponseEntity.status(HttpStatus.OK).body(medicineService.updateMedicine(medicineDTO));
    }


    /**
     * Soft deletes the medicine using the corresponding id.
     * medicine id from the request will be used to get the medicine
     * and will be flagged as deleted.
     *
     * @param medicineId corresponding medicine id to delete
     * @return response for deletion
     * @throws CustomException throws exception when occurs when medicine was not found
     */
    @PutMapping("/delete/{medicineId}")
    public ResponseEntity<String> deleteMedicine(@PathVariable("medicineId") Long medicineId) throws CustomException {
        Long medicineById = medicineService.deleteMedicine(medicineId);

        if (medicineById != null) {
            return ResponseEntity.status(HttpStatus.OK).body(medicineById + Constants.DELETED_SUCCESSFULLY);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(Constants.NOT_DELETED_SUCCESSFULLY);
        }
    }
}
