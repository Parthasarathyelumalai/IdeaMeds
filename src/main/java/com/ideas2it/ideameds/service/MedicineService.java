/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.MedicineDTO;
import com.ideas2it.ideameds.exception.CustomException;

import java.util.List;

/**
 * <p>
 * Service Interface
 * Performs Create, Read, Update and Delete operations for Medicine
 * </p>
 *
 * @author Dinesh Kumar R
 * @version 1.0
 * @since 2022-11-18
 */
public interface MedicineService {
    /**
     * Adds the new medicine record to the database.
     * Each entry contains validation to ensure the valid medicine.
     * Multiple medicine records cannot have the same name.
     * Medicine contains the formal info about medicine
     * A medicine can be assigned to many brand Items
     *
     * @param medicineDTO to add a new medicine
     * @return medicine which was added successfully in the database
     * @throws CustomException throws when the new medicine name already exist
     */
    MedicineDTO addMedicine(MedicineDTO medicineDTO) throws CustomException;

    /**
     * It returns a list of all medicines in the database
     *
     * @return list of medicines available
     */
    List<MedicineDTO> getAllMedicines();

    /**
     * This function gets medicine using the id
     * A medicine id should be exact same compared with brand id
     * from the database
     *
     * @param medicineId id to get the medicine
     * @return medicine using the id
     * @throws CustomException throws when the medicine is not found
     */
    MedicineDTO getMedicineById(Long medicineId) throws CustomException;

    /**
     * gets medicine using name got from the request.
     * A medicine name should be exact same compared with brand
     * name available in the database.
     *
     * @param medicineName medicine name for getting medicine
     * @return medicine using the medicine name
     * @throws CustomException throws exception when there is no medicine found
     */
    MedicineDTO getMedicineByName(String medicineName) throws CustomException;

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
    MedicineDTO updateMedicine(MedicineDTO medicineDTO) throws CustomException;

    /**
     * Soft deletes the medicine using the corresponding id.
     * medicine id from the request will be used to get the medicine
     * and will be flagged as deleted.
     *
     * @param medicineId corresponding medicine id to delete
     * @return Medicine id after the medicine got flagged deleted
     * @throws CustomException throws exception when occurs when medicine was not found
     */
    Long deleteMedicine(Long medicineId) throws CustomException;
}
