/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.model.UserMedicine;

import java.util.List;
import java.util.Optional;

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
     * @param userMedicines - pass user medicine list
     * @return list of user medicines - gives medicines
     */
    Optional<List<UserMedicine>> addUserMedicine(List<UserMedicine> userMedicines);
}
