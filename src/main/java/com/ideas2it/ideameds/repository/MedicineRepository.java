/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.repository;

import com.ideas2it.ideameds.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * <p>
 * Repository for Medicine
 * The Medicine objects are saved to the database
 * </p>
 * @author - Dinesh Kumar R
 * @version - 1.0
 * @since - 2022-11-18
 */
@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    /**
     * Gets medicine by medicine Name
     * @param medicineName
     *        medicine name to get medicine
     * @return medicine using medicine name
     */
    Optional<Medicine> getMedicineByMedicineName(String medicineName);
}
