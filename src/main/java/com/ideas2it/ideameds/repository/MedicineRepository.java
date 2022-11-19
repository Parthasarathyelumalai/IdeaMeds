package com.ideas2it.ideameds.repository;

import com.ideas2it.ideameds.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    Medicine getMedicineByMedicineName(String medicineName);
}
