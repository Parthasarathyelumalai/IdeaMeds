/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.repository;

import com.ideas2it.ideameds.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Address service
 *
 * @author Parthasarathy Elumalai
 * @version 1.0
 * @since 2022-11-22
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
