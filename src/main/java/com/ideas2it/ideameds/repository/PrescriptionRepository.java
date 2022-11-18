package com.ideas2it.ideameds.repository;

import com.ideas2it.ideameds.model.Prescription;
import com.ideas2it.ideameds.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription,Long> {

    List<Prescription> getPrescriptionByUser(User user);
}
