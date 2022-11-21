package com.ideas2it.ideameds.repository;

import com.ideas2it.ideameds.model.Prescription;
import com.ideas2it.ideameds.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository of Prescription
 * The Prescription objects are saved to the database
 * @author - Nithish K
 * @version - 1.0
 * @since - 2022-11-18
 */
@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription,Long> {
    List<Prescription> getPrescriptionByUser(User user);
}
