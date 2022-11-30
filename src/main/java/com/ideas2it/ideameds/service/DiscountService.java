/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;


import com.ideas2it.ideameds.dto.DiscountDTO;
import com.ideas2it.ideameds.exception.CustomException;

import java.util.List;

/**
 * Service for Discount.
 *
 * @author - Soundharrajan.S
 * @version - 1.0
 * @since - 2022-11-30
 */

public interface DiscountService {

    /**
     * add discount details and save in repository by admin.
     * @param discountDTO - To save discount details in repository.
     * @return Discount.
     */
    DiscountDTO addDiscount(DiscountDTO discountDTO);

    /**
     * Retrieve all discount details from repository.
     * @return All discount details.
     */
    List<DiscountDTO> getAll();

    /**
     * To delete discount by discount id.
     * @param discountId - To delete discount by discount id.
     * @return - Response entity.
     * @throws CustomException - Can not delete.
     */
    boolean deleteDiscountById(Long discountId) throws CustomException;
}
