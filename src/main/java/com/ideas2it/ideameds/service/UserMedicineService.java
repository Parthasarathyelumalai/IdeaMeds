/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.UserMedicineDTO;
import com.ideas2it.ideameds.exception.CustomException;

import java.util.List;

/**
 * Interface for User Medicine Service
 *
 * @author - Parthasarathy Elumalai
 * @version - 1.0
 * @since - 2022-11-21
 */
public interface UserMedicineService {

    /**
     * Add a new medicine to the user's medicine list.
     * before adding it, it should be validated.
     * if medicine is not exists,it will show the error message.
     *
     * @param userId       The userId of the user who is adding the medicine.
     * @param userMedicine This is the object that contains the information about the medicine that the user wants to add.
     * @return id  - id of cart
     */
    Long addUserMedicine(Long userId, UserMedicineDTO userMedicine) throws CustomException;

    /**
     * Get the previous user medicine. if list of medicine is exists, it will show.
     * otherwise, it will show empty list.
     *
     * @param userId The userId of the user whose previous medicines are to be fetched.
     * @return List of UserMedicineDTO - return user medicine list that add-in cart
     */
    List<UserMedicineDTO> getPreviousUserMedicine(Long userId);
}
