/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.repository;

import com.ideas2it.ideameds.model.UserMedicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for User Medicine
 *
 * @author - Parthasarathy Elumalai
 * @since - 2022-11-21
 * @version - 1.0
 */
@Repository
public interface UserMedicineRepository extends JpaRepository<UserMedicine, Long> {

    /**
     * get user's user medicine list
     * @param userId - pass user Id
     * @return List of userMedicine - return medicines
     */
    @Query(
            value = "SELECT * FROM user_medicine u WHERE u.user_Id = ?1",
            nativeQuery = true)
    List<UserMedicine> findByUserId(Long userId);
}
