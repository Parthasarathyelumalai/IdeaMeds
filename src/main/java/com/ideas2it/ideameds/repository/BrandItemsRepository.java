/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.repository;

import com.ideas2it.ideameds.model.BrandItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * Repository for Brand Items
 * The Brand Items objects are saved to the database
 * </p>
 * @author - Dinesh Kumar R
 * @version - 1.0
 * @since - 2022-11-21
 */
@Repository
public interface BrandItemsRepository extends JpaRepository<BrandItems, Long> {

    /**
     * <p>
     *     gets list of medicines by searching through
     *     medicine name or word related to the brand item
     * </p>
     * @param medicineName
     *        name or a word to get list of medicines
     * @return list of brand items using search
     */
    Optional<List<BrandItems>> findAllByBrandItemNameContainingIgnoreCase(String medicineName);
}
