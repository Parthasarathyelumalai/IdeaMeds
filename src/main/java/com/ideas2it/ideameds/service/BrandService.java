/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.BrandDTO;
import com.ideas2it.ideameds.exception.CustomException;

import java.util.List;

/**
 * Service Interface
 * Performs Create, Read, Update and Delete operations for the Brand
 *
 * @author Dinesh Kumar R
 * @version 1.0
 * @since 2022-11-18
 */
public interface BrandService {

    /**
     * Adds the new brand record to the database.
     * Each entry contains validated entries
     * Brand contains entries about the medicine brand
     * A brand can be assigned to many brand Items
     *
     * @param brandDTO new brandDTO which will be converted to brand
     * @return brand which was added in the database successfully
     * @throws CustomException throws when the new brand name is already exist
     */
    BrandDTO addBrand(BrandDTO brandDTO) throws CustomException;

    /**
     * Gets all the brandDTO available in the database
     *
     * @return list of all brand if it's available in the database
     *         null if there is no record
     */
    List<BrandDTO> getAllBrands();

    /**
     * gets brand item using brand name got from the request.
     * A brand name should be exact same compared with brand
     * name available in the database.
     *
     * @param brandName contains a brand name from the
     *                  corresponding controller
     * @return brand after getting it using the valid brand name
     * @throws CustomException throws when the brand not fount using the
     *                         brand name from the controller
     */
    BrandDTO getBrandByBrandName(String brandName) throws CustomException;


    /**
     * Gets brand using the requested id
     * A Brand id should be exact same compared with brand id
     * from the database
     *
     * @param brandId used to search through and get a valid brand
     * @return brand after the id gets a valid brand available in the database
     * @throws CustomException throws when the brand not found using the id from the controller
     */
    BrandDTO getBrandById(Long brandId) throws CustomException;

    /**
     * Updates the existing brand in the database
     * The brand will be found by the id of the brand and gets updated
     * Changing a brand ID to invalid format throws exception
     *
     * @param brandDTO contains a valid and updated brand DTO to
     *                 update to an existing record
     * @return Brand Dto after it was updated successfully
     * @throws CustomException throws when the brand was not found, happens when the brandId
     *                         was changed before updating
     */
    BrandDTO updateBrand(BrandDTO brandDTO) throws CustomException;

    /**
     * Soft deletes the brand using the corresponding id.
     * brand id from the controller will be used to get the brand
     * and will be flagged as deleted.
     *
     * @param brandId used to get the brand to update it as deleted
     * @return response that the brand has deleted successfully
     *         after the brand has flagged deleted
     * @throws CustomException throws when brand is not found using the
     *                         corresponding id from the request
     */
    Long deleteBrand(Long brandId) throws CustomException;
}
