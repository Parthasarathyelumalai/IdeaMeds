/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.repository;

import com.ideas2it.ideameds.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * <p>
 * Repository for Medicine Brand
 * The Brand objects are saved to the database
 * </p>
 * @author - Dinesh Kumar R
 * @version - 1.0
 * @since - 2022-11-18
 */
@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    /**
     * <p>
     *     gets brand by brand Name
     * </p>
     * @param brandName
     *        brand name to get brand
     * @return brand using brand name
     */
    Optional<Brand> getBrandByBrandName(String brandName);
}
