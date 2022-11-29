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
 * Repository for order.
 *
 * @author - Soundharrajan.S
 * @version - 1.0
 * @since - 2022-11-17
 */
@Repository
public interface OrderSystemRepository extends JpaRepository<OrderSystem, Long> {

    Optional<List<OrderSystem>> findByUser(User user);

    Optional<List<OrderSystem>> findByOrderId(Long orderId);

}
