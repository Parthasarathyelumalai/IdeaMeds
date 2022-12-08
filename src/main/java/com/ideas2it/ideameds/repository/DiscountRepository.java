/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.repository;

import com.ideas2it.ideameds.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Creating a repository for the Discount model.
 *
 * @author - Soundharrajan S.
 * @version - 1.0
 * @since - 2022-11-30
 */

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {}
