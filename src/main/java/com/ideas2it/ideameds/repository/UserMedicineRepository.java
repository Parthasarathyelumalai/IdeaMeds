/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.repository;

import com.ideas2it.ideameds.model.UserMedicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Repository for User Medicine
 *
 * @author - Parthasarathy Elumalai
 * @since - 2022-11-21
 * @version - 1.0
 */
@Repository
public interface UserMedicineRepository extends JpaRepository<UserMedicine, Long> {
}
