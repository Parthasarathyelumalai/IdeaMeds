/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.repository;

import com.ideas2it.ideameds.model.Order;
import com.ideas2it.ideameds.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * A repository for Order System.
 *
 * @author - Soundharrajan S.
 * @version - 1.0
 * @since - 2022-11-30
 */

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Find all orders for a given user.
     *
     * @param user The user whose orders we want to find.
     * @return Optional<List<Order>>
     */
    Optional<List<Order>> findByUser(User user);
}
