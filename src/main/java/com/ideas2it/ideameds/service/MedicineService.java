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
     * <p>
     * Adds a new medicine
     * </p>
     *
     * @param medicineDTO to add a new medicine
     * @return added medicine
     * @throws CustomException
     *         throws when the new medicine name already exist
     */
    MedicineDTO addMedicine(MedicineDTO medicineDTO) throws CustomException;

    /**
     * <p>
     * gets All the medicines
     * </p>
     *
     * @return list of all medicines
     */
    List<MedicineDTO> getAllMedicines();

    /**
     * <p>
     * Gets medicine By Id
     * </p>
     *
     * @param medicineId id to get the medicine
     * @return medicine using the id
     * @throws CustomException throws when the medicine is not found
     */
    MedicineDTO getMedicineById(Long medicineId) throws CustomException;

    /**
     * <p>
     * Updates the medicine
     * </p>
     *
     * @param medicineDTO medicine to be updated
     * @return updated medicine
     * @throws CustomException throws when the medicine is not found
     */
    MedicineDTO updateMedicine(MedicineDTO medicineDTO) throws CustomException;

    /**
     * <p>
     * delete a medicine
     * </p>
     *
     * @param medicineId id to get a medicine
     * @return medicine id after medicine is deleted successfully
     * @throws CustomException throws when the medicine is not found
     */
    Long deleteMedicine(Long medicineId) throws CustomException;

    /**
     * <p>
     * Gets medicine By Name
     * </p>
     *
     * @param medicineName medicine name to search the medicine
     * @return medicine by using medicine Name
     * @throws CustomException throws when the medicine is not found
     */
     MedicineDTO getMedicineByName(String medicineName) throws CustomException;
}
