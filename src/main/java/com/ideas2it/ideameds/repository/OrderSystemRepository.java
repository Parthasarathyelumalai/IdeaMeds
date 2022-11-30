/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.repository;

import com.ideas2it.ideameds.model.OrderSystem;
import com.ideas2it.ideameds.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Repository for Order System.
 *
 * @author - Soundharrajan S.
 * @version - 1.0
 * @since - 2022-11-30
 */
@Repository
public interface OrderSystemRepository extends JpaRepository<OrderSystem, Long> {

    /**
     * Get all order by user.
     * @param user - To get list of order.
     * @return - List of order system.
     */
    Optional<List<OrderSystem>> findByUser(User user);
}
