package com.ideas2it.ideameds.repository;

import com.ideas2it.ideameds.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    Brand getBrandByBrandName(String brandName);
}
