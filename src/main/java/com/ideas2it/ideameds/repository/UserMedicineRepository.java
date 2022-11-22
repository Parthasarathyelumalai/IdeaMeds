package com.ideas2it.ideameds.repository;

import com.ideas2it.ideameds.model.UserMedicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMedicineRepository extends JpaRepository<UserMedicine, Long> {
}
