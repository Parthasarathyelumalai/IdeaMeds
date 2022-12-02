/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.UserMedicineDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.model.UserMedicine;

import java.util.List;

/**
 * Interface for User Medicine Service
 *
 * @author - Parthasarathy Elumalai
 * @since - 2022-11-21
 * @version - 1.0
 */
public interface UserMedicineService {
    /**
     * Add user medicine
     * @param userId - pass user id
     * @return id - id of cart
     */
     Long addUserMedicine(Long userId, UserMedicineDTO userMedicine) throws CustomException;
}
