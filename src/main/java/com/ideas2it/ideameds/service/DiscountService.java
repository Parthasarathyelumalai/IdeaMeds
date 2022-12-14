/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;


import com.ideas2it.ideameds.dto.DiscountDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.model.Discount;

import java.util.List;
import java.util.Optional;

/**
 * This is a service interface for Discount.
 *
 * @author - Soundharrajan.S
 * @version - 1.0
 * @since - 2022-11-30
 */
public interface DiscountService {

    /**
     * Add discount details and save in repository by admin.
     *
     * @param discountDTO The discount object that you want to add.
     * @return DiscountDTO.
     * @throws CustomException No discount.
     */
    Optional<DiscountDTO> addDiscount(DiscountDTO discountDTO) throws CustomException;

    /**
     * Get all discount DTOs from repository.
     *
     * @return A list of all discounts.
     */
    List<DiscountDTO> getAllDiscountDTO();

    /**
     * Get all discount from repository.
     * @return a list of all discount.
     */
    List<Discount> getAllDiscount();

    /**
     * It updates the discount by discountDto object.
     *
     * @param discountDTO The discountDto object to be updated.
     * @return DiscountDto Show the discount dto after update.
     * @throws CustomException  Can not update discount, I'd not found.
     */
    Optional<DiscountDTO> updateDiscountById(DiscountDTO discountDTO) throws CustomException;

    /**
     * Deletes a discount by its id
     *
     * @param discountId The id of the discount to be deleted.
     * @return A boolean value.
     * @throws CustomException - Can not delete.
     */
    boolean deleteDiscountById(Long discountId) throws CustomException;

}
