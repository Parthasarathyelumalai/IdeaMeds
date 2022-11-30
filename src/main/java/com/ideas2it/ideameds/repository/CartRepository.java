/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.repository;

import com.ideas2it.ideameds.model.Cart;
import com.ideas2it.ideameds.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Cart repository.
 *
 * @author - Soundharrajan.S
 * @version - 1.0
 * @since - 2022-11-30
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    /**
     * Get Cart by user entity.
     * @param user - To get cart.
     * @return - Cart.
     */
    Optional<Cart> findByUser(User user);
}
