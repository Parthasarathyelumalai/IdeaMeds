/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.model.UserMedicine;
import com.ideas2it.ideameds.repository.UserMedicineRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class UserMedicineServiceImpl implements UserMedicineService {

    private UserMedicineRepository userMedicineRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<List<UserMedicine>> addUserMedicine(List<UserMedicine> userMedicines) {
        return Optional.of(userMedicineRepository.saveAll(userMedicines));
    }
}
