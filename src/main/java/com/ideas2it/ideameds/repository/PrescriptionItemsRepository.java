package com.ideas2it.ideameds.repository;

import com.ideas2it.ideameds.model.PrescriptionItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionItemsRepository extends JpaRepository<PrescriptionItems,Long> {
}
