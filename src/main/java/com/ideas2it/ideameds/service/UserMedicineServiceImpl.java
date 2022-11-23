/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.model.User;
import com.ideas2it.ideameds.model.UserMedicine;
import com.ideas2it.ideameds.repository.UserMedicineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class UserMedicineServiceImpl implements UserMedicineService {

    private UserMedicineRepository userMedicineRepository;

    @Autowired
    public UserMedicineServiceImpl(UserMedicineRepository userMedicineRepository) {
        this.userMedicineRepository = userMedicineRepository;
    }

    @Override
    public Optional<List<UserMedicine>> addUserMedicine(List<UserMedicine> userMedicines) {
        Optional<List<UserMedicine>> savedUserMedicine = Optional.of(userMedicineRepository.saveAll(userMedicines));
        return savedUserMedicine;
    }
}
