/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.repository;

import com.ideas2it.ideameds.model.OrderSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository for order.
 *
 * @author - Soundharrajan.S
 * @version - 1.0
 * @since - 2022-11-17
 */
@Repository
public interface OrderSystemRepository extends JpaRepository<OrderSystem, Long> {}
