package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.model.User;
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
    Optional<List<UserMedicine>> addUserMedicine(List<UserMedicine> userMedicines);
}
