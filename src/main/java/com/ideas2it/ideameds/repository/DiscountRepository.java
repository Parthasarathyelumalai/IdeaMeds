package com.ideas2it.ideameds.repository;

import com.ideas2it.ideameds.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * controller for discount repository.
 *
 * @author - Soundharrajan.S
 * @version - 1.0
 * @since - 2022-11-21
 */

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {}
