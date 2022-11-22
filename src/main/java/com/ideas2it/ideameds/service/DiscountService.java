package com.ideas2it.ideameds.service;


import com.ideas2it.ideameds.model.Discount;

import java.util.List;

/**
 * Service for discount.
 *
 * @author - Soundharrajan.S
 * @version - 1.0
 * @since - 2022-11-21
 */

public interface DiscountService {

    /**
     * add discount details and save in repository by admin.
     * @param discount - To save discount details in repository.
     * @return Discount.
     */
    Discount addDiscount(Discount discount);

    /**
     * Retrieve all discount details from repository.
     * @return All discount details.
     */
    List<Discount> getAll();
}
