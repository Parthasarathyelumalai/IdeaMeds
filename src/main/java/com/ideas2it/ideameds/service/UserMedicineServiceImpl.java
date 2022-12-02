/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.model.UserMedicine;
import com.ideas2it.ideameds.repository.UserMedicineRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Class for User Medicine Service
 *
 * @author - Parthasarathy Elumalai
 * @since - 2022-11-21
 * @version - 1.0
 */
@Service
@Slf4j
public class UserMedicineServiceImpl implements UserMedicineService {

    private final UserMedicineRepository userMedicineRepository;

    /**
     * Create instance for the class
     * @param userMedicineRepository create for user medicine repository
     */
    @Autowired
    public UserMedicineServiceImpl(UserMedicineRepository userMedicineRepository) {
        this.userMedicineRepository = userMedicineRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<List<UserMedicine>> addUserMedicine(List<UserMedicine> userMedicines) {
        return Optional.of(userMedicineRepository.saveAll(userMedicines));
    }
}
