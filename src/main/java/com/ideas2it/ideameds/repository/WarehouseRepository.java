/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.repository;

import com.ideas2it.ideameds.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Repository for Warehouse
 * The Warehouse objects are saved to the database
 * </p>
 * @author - Dinesh Kumar R
 * @version - 1.0
 * @since - 2022-11-18
 */
@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
}
