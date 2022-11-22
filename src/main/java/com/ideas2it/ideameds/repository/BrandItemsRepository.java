package com.ideas2it.ideameds.repository;

import com.ideas2it.ideameds.model.BrandItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandItemsRepository extends JpaRepository<BrandItems, Long> {
}
